import requests
import json



r = requests.get('https://145.24.222.82:8443/test', verify='../../certs/noob-root.pem')
print(r.text)

headers = {"Content-Type": "application/json"}
balancePayload = {
   "head":{
      "fromCtry":"T1",
      "fromBank":"TES2",
      "toCtry":"T1",
      "toBank":"TEST"
   },
   "body":{
      "acctNo":"1234567",
      "pin":"0101"
   }
}
bal_data = json.dumps(balancePayload)


print('Balance request: ' + bal_data)
b = requests.post('https://145.24.222.82:8443/api/balance',
    cert = ('../../certs/t1-server-chain.pem', '../../certs/t1-server-key.pem'),
    verify='../../certs/noob-root.pem',
    headers=headers,
    data=bal_data)
print('Balance status: ' + str(b.status_code) + ', response: ' + b.text)

withdrawPayload = {
   "head":{
      "fromCtry":"T1",
      "fromBank":"TES2",
      "toCtry":"T1",
      "toBank":"TEST"
   },
   "body":{
      "acctNo":"1234567",
      "pin":"0101",
      "amount": 5000
   }
}
wdw_data = json.dumps(withdrawPayload)
print('Withdraw request: ' + wdw_data)
w = requests.post('https://145.24.222.82:8443/api/withdraw',
    cert = ('../../certs/t1-server-chain.pem', '../../certs/t1-server-key.pem'),
    verify='../../certs/noob-root.pem',
    headers=headers,
    data=wdw_data)
print('Withdraw status: ' + str(w.status_code) + ', response: ' + w.text)
