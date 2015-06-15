README for bankapp
==========================

a sample quick & dirty app for "Bank App"
This is a home work for IT students ...

To compile:
mvn clean install -DskipTests

To run:
from your IDE (eclipse..): simply launch main() from class fr.iut.bankapp.Application
from maven command line: mvn spring-boot:run

To play with: open your browser at "http://localhost:8080", login using user:"admin" / password:"admin"



J'ai utilisé JHipster pour generer les CRUD sur les classes du domain
J'ai retouché un tout petit peu les 2-3 entities + ecrans ou j'avais besoin de plusieurs relations... c'est une limitation bizarre de jhipster de ne pas générer plusieurs relations pour une meme classes)
Je n'ai ecris AUCUNE LIGNE DE CODE pour l'instant

Pour que l'application affiche des données, j'ai pre-rempli une base de donnée "hsqldb" (utilisable en standalone, sans "vrai" base et sans reseau) avec des scripts de chargement liquibase a la fois pour le schema (redundant avec hibernate) mais aussi pour des données au format CSV


J'ai définit les classes suivantes du domain:
 Instrument, Thirdparty, Portfolio, FinOperation  (au lieu de Operation qui peut etre ambigu avec certaines librairies)
(et les classes facultatives suivantes: AuthorizedPortfolioOperation, PortfolioInstrumentPosition )

En detail
- Instrument =  une entity (censée etre immutable une fois crée), qui represente un "instrument financier"
  par exemple : "EUR" est la devise euro, c'est l'instrument de base de toute operation financiere en cash dans l'union europeene... Mais une operation financiere peut echanger autre choses: par exemple une action, une comodity (petrole, or, etc..), une option, un future, etc...
- Thirdparty = définition d'une contre-partie  : cela peut etre une  personnes physique ou morale, un broker, etc...
- portfolio: un compte appartenant a un thirdparty (personne morale ou physique ... voire a plusieurs personnes "ayant droits" (cf compte-joins entre personnes mariés?). Il a plusieurs type de compte: compte a vue "CAV", "Compte Epargne" ("CEL", "PEL" ...), "Compte Titre", etc..
- une operation est un echange d'une quantité d'instrument d'un portfolio vers un autre portfolio a une date (et ayant une date de valeur, en general J+2)

 
lancer l'appli chez-vous, et logguer vous avec le compte user:"admin", password:"admin"


