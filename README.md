
# FenigeSDK

![Kotlin](https://img.shields.io/badge/Kotlin-violet)
![Android 7.0+](https://img.shields.io/badge/Android-7.0%2B-blue)

## System Requirements
Android version 7.0 (API lvl 24 or greater)

## Installation

Installit use the maven repo:

Gradle:

```groovy
implementation ("com.github.Fenige:FenigeSDK-Android:{VERSION_NUMBER}'")
```

### Init Payment

You can init payment and will have callback with `Transaction ID`:
```kotlin
FenigePaytool.initPayment(
activity: AppCompatActivity, 
apiKey: String, 
currencyCode: String, 
amount: Int, 
description: String, 
merchantUrl: String, 
orderNumber: String, 
formLanguage: String, 
redirectUrl: com.sdk.fenigepaytool.entity.RedirectUrl, 
sender: com.sdk.fenigepaytool.entity.Sender, 
transactionId: String, 
autoClear: Boolean, 
config: com.sdk.fenigepaytool.entity.Config,
paytoolResultCallback: com.sdk.fenigepaytool.entity.PaytoolResultCallback,
isReccuring: Boolean
) 
```

|Field|Type|Description|
|--|--|--|
|environment|Enum|Environment .development or .production
|apiKey|String|This is the value you receive from the payment gateway provider for production and staging environment. It is necessary to be identified in our system
|currencyCode|String|Currency for transaction (in accordance with ISO-4217), example: USD
|amount|Int|The total transfer amount (in pennies - 1PLN = 100)
|receiverAmount|String|Information field only. Field determine receiving amount of cash transferred in one hundredth of the currency. [1PLN = 100]
|description|String|Description of the transaction, which indicates what the user is paying for
|merchantUrl|String|URL address of merchant web system
|orderNumber|String|Declarative number of order that is just purchased by cardholder, set by merchant, should be unique
|formLanguage|String|Language of transaction process in web browser in accordance with ISO 3166-1 Alpha-2, use only lowercas
|redirectUrl.successUrl|String|URL of merchant web service to forward after successful payment flow
|redirectUrl.failureUrl|String|URL of merchant web service to forward after failure payment flow
|sender.firstName|String|Cardholder's first name
|sender.lastName|String|Cardholder's last name
|sender.adress.countryCode|String|Two character ISO 3166-1 alpha-2 code of country
|sender.adress.city|String|Name of the cardholder city
|sender.adress.postalCode|String|Postal code of this address
|sender.adress.street|String|Street name in the city
|sender.adress.houseNumber|String|House number with optional flat number
|transactionConfigurationId|String|Terminal’s unique uuid to process payment
|autoClear|Bool|Automaticly this parameter is on true. It mean that transaction will be cleared automaticly by fenige in few hours. You can set this parameter as false butyou must remember to clear your transaction.

## Author

Fenige, fenige.mobile.sdk@gmail.com

## License

FenigeSDK is available under the MIT license. See the LICENSE file for more info.
