$ErrorActionPreference = "Stop"
$base = "http://localhost"
$email = "verify-$(Get-Date -Format 'yyyyMMddHHmmss')@ethio.com"

Write-Host "=== 1. Register ===" -ForegroundColor Cyan
$reg = Invoke-RestMethod -Uri "$base`:8081/api/auth/register" -Method POST -ContentType "application/json" `
    -Body (@{ email = $email; fullName = "Verify User"; password = "secret12" } | ConvertTo-Json)
$reg | ConvertTo-Json

Write-Host "`n=== 2. Login ===" -ForegroundColor Cyan
$login = Invoke-RestMethod -Uri "$base`:8081/api/auth/login" -Method POST -ContentType "application/json" `
    -Body (@{ email = $email; password = "secret12" } | ConvertTo-Json)
$token = $login.accessToken
Write-Host "Token received (length $($token.Length))"

Write-Host "`n=== 3. Create order (happy path PROD-001) ===" -ForegroundColor Cyan
$headers = @{ Authorization = "Bearer $token" }
$order = Invoke-RestMethod -Uri "$base`:8082/api/orders" -Method POST -ContentType "application/json" `
    -Headers $headers -Body (@{ productId = "PROD-001"; quantity = 2 } | ConvertTo-Json)
$orderId = $order.orderId
$order | ConvertTo-Json
Write-Host "OrderId: $orderId"

Write-Host "`nWaiting 8s for async events..." -ForegroundColor Yellow
Start-Sleep -Seconds 8

Write-Host "`n=== 4. Verify downstream ===" -ForegroundColor Cyan
$payment = Invoke-RestMethod -Uri "$base`:8083/api/payments/$orderId"
Write-Host "Payment: $($payment | ConvertTo-Json -Compress)"
$shipment = Invoke-RestMethod -Uri "$base`:8085/api/shipments/$orderId"
Write-Host "Shipment: $($shipment | ConvertTo-Json -Compress)"

Write-Host "`n=== 5. Bad JWT (expect 401) ===" -ForegroundColor Cyan
try {
    Invoke-RestMethod -Uri "$base`:8082/api/orders" -Method POST -ContentType "application/json" `
        -Headers @{ Authorization = "Bearer invalid-token" } `
        -Body (@{ productId = "PROD-001"; quantity = 1 } | ConvertTo-Json)
    Write-Host "FAIL: expected 401" -ForegroundColor Red
} catch {
    if ($_.Exception.Response.StatusCode -eq 401) {
        Write-Host "PASS: Got 401 Unauthorized" -ForegroundColor Green
    } else { throw }
}

Write-Host "`n=== 6. Out of stock (PROD-002 qty 10) ===" -ForegroundColor Cyan
$orderFail = Invoke-RestMethod -Uri "$base`:8082/api/orders" -Method POST -ContentType "application/json" `
    -Headers $headers -Body (@{ productId = "PROD-002"; quantity = 10 } | ConvertTo-Json)
$failOrderId = $orderFail.orderId
Write-Host "OrderId: $failOrderId"
Start-Sleep -Seconds 8
try {
    Invoke-RestMethod -Uri "$base`:8085/api/shipments/$failOrderId" -ErrorAction Stop
    Write-Host "FAIL: shipment should not exist" -ForegroundColor Red
} catch {
    Write-Host "PASS: No shipment for out-of-stock order" -ForegroundColor Green
}

Write-Host "`n=== DONE ===" -ForegroundColor Green
Write-Host "Check notification-service console for NOTIFICATION => lines"
