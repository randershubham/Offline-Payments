# Offline Payments

It is a mobile application through which the payments can be made in offline mode. The payment information is communicted from one mobile device to other device using a Wifi P2P protocol. It uses Visa Direct API for fund transfer.The API's are OCT (Original Credit Transaction) and AFT(Account Funding Transaction) API's of Visa Direct. 

The project is divided into two parts: 1. Backend and 2. FrontEnd.

Backend:
1. The backend is developed in Java using Spring boot framework using embedded tomcat.
2. It has support for both Synchronous Payments and Asynchronous payments
3. Apache Kafka (version - 1.0.0) is used for Asynchronous payments, aysnchronous payments means the transaction will be processed in future.
4. Note that project is built using Visa Direct's Sandbox API.
5. Originally the response acknowledgement was done using SMS, but the code has been removed and the developer can plugin thier own notification systems like GCM etc.

Frontend:
1. Frontend is developed in Android
2. The mobile app communicates between devices using WiFi P2P protocol.

Note:
Currently the offline payments means even one person can pay to other if the person does not have internet and but other should have the internet. 

Future work:
1. Complete offline mode when both the persons does not have internet by storing the payment data in Secure Element or some secure place.
2. Testing on production API's
