# Espace de discution éphémère
Voici une application web qui propose un espace d'échange qui oublie:</br>
* Chaque sujet crée est représentés par une bouteille dans une rivière.
* Les bouteilles sont cliquable afin d'afficher les réponses au sujet, et elle se déplace 
en fonction de la date à laquelle le sujet à été crée (de gauche à droite).
* La bouteille grossi en fonction du nombre de réponses.
* Si il n'y a pas de réponse au sujet pendant 7 jours, alors le sujet est oublié, il n'est 
plus visible sur le site. 
* Lors d'une nouvelle réponse la bouteille repasse au début(à gauche) 
pour un nouveau cycle de 7 jours.
* Chaque sujet/réponse à une note, l'utilisateur peu voter 1 fois afin de le faire monter 
ou descendre par rapport aux autres pour améliorer la visibilité du sujet/réponse.

### Techniques utilisés pour ce projet:
Ce projet se compose de deux applications et d'une base de donnée:_
* Application Web crée avec le framework Angular en version 9 (typeScript).
    * Le site est responsive (pensé pour être utilisé facilement sur un petit écran tactile)
* API web en REST (microserviceBdd) (java, jdk8) connectée à la base de données
MongoDb.
    * Les dépendances sont gérés avec Maven (version 4.0.0).
    * L'architecture est multi-modules (business, dao, model et web).
    * L'API web est couverte par des tests d'intégrations ou unitaires.
* Base de donnée NoSql mongodb.
* Toutes les applications sont dockérizé. (docker-compose dans le dossier docker)
* Utilisation de Token pour identifier l'utilisateur.

### Les fonctions utilisateurs:
* Création de sujets (dans la limite de 20).
* Création de réponses au sujets.
* Création de commentaires au réponses.
* Système de votation afin d'amélioré la visibilité d'un sujet ou d'une réponse.

## Déploiement:
Placez-vous dans le dossier docker et executez la commande suivante:
```
    docker-compose up
```
## modification microserviceBdd(back):
- Effectuez la modification
- executer la commande:
```
    mvn package
```
- Déplacez le .jar obtenu du dossier: microservice-web/target/[version du microserviceBdd.jar]
vers le dossier: microserviceBdd/executable
- Modifier le nom du nouveau jar dans le fichier Dockerfile

## Contribution
[Github du projet](https://github.com/nicowtt/solution_project)

* 1: clone repository
* 2: Créer une nouvelle branche
* 2: Faite vos évolutions / changements (git checkout -b my-new-feature)
* 3: "Commit" les évolutions / changements (git commit -am "add some feature")
* 4: "Push" la nouvelle branche (git push origin my-new-feature)
* 5: Créer une nouvelle "pull request"

## Aperçu du site
![alt text](https://github.com/nicowtt/solution_project/blob/master/discutProject.jpg)<br>
####[Lien du site](http://discut.hopto.org/)


## Licence
    Copyright (C) 2020  BODELLE Nicolas

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>..

Voir mes autres projets :
[ICI](https://github.com/nicowtt?tab=repositories)


*twitter: nicow@nicowtt*