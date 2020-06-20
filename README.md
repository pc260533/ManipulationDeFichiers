# ManipulationDeFichiers
L’application manipulation de fichiers permet de réaliser des fonctionnalités de bases de gestion de fichiers textuels.  
Le code de l’application, le fichier README.md et le JAR sont également disponibles sur le dépôt GitHub :  
https://github.com/pc260533/ManipulationDeFichiers

L’application propose 10 fonctionnalités principales toutes accessibles depuis un panel de boutons dans la fenêtre générale.  
La fenêtre principale de l’application est divisée en deux parties : 
-	__Partie sélection__ du répertoire courant puis du dossier sélectionné et sélection des fonctionnalités : affiché qui ne change pas,
-	__Partie affichage__ d’un panel correspondant à la fonctionnalité : change en fonction de la fonctionnalité.

![1 : Fenêtre principale de l’application au lancement](../screenshots/screenshots/1.png?raw=true "Fenêtre principale de l’application au lancement.")

## Les 8 fonctionnalités de l’application sont :
-	Sélectionner un répertoire courant,
-	Sélectionné un dossier contenu dans le répertoire courant,
-	Ajouter un fichier dans le dossier sélectionné,
-	Supprimer un fichier du dossier sélectionné,
-	Modifier le nom, l’extension ou le contenu d’un fichier du dossier sélectionné,
-	Dupliquer un fichier du dossier sélectionné,
-	Chiffrer un fichier du dossier sélectionné,
-	Déchiffrer un fichier du dossier sélectionné,
-	Concaténer des fichiers du dossier sélectionné entre eux avec des fichiers extérieurs,
-	Lister toutes les occurrences d’un mot dans tous les fichiers du dossier sélectionné avec le numéro de la ligne où le mot apparait associé au nombre de fois où le mot apparait dans cette ligne.

Tous les fichiers créés ou modifiés utilisent l’encodage UTF-8.

---

## Sélection d’un répertoire courant :
Pour utiliser les autres fonctionnalités, il est impératif de sélectionner un répertoire courant et un dossier de ce répertoire. Par défaut, le dossier sélectionné est le répertoire courant lui-même.  
On sélectionne le répertoire courant en cliquant sur le bouton Parcourir. Le chemin de ce dossier s’affiche dans la zone de texte à gauche du bouton.  
![2 : Sélection d’un répertoire courant.](../screenshots/screenshots/2.png?raw=true "Sélection d’un répertoire courant.")
Tous les fichiers et dossiers du répertoire s’affichent récursivement dans l’arbre.
![3 : Affichage du dossier sélectionné par défaut : le répertoire courant.](../screenshots/screenshots/3.png?raw=true "Affichage du dossier sélectionné par défaut : le répertoire courant.")

---

## Sélection d’un dossier parmi le répertoire courant
Pour utiliser les fonctionnalités, il faut spécifier un dossier dont on liste les fichiers. Par défaut ce dossier est le répertoire courant. On peut néanmoins naviguer parmi les sous-dossiers du répertoire courant dans l’arbre et cliquer sur un autre dossier pour le sélectionner.  
Le nom du dossier sélectionné est inscrit dans un label à droite de l’arbre.
![4 : Sélection d’un nouveau dossier sélectionné, un sous-dossier du répertoire courant.](../screenshots/screenshots/4.png?raw=true "Sélection d’un nouveau dossier sélectionné, un sous-dossier du répertoire courant.")
Au clic sur un dossier de l’arbre, les fichiers situés dans ce dossier apparaissent dans la partie affichage dans un panel de listage de fichiers.  
Un clic sur un dossier permet toujours de revenir à cet affichage ou l’utilisation des autres fonctionnalités est possible.

---

## Ajout d’un fichier dans le dossier sélectionné :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
Puis, on clique sur le bouton Ajouter Fichier pour ajouter un fichier dans ce dossier.  
![5 : Sélection du dossier où ajouter un fichier.](../screenshots/screenshots/5.png?raw=true "Sélection du dossier où ajouter un fichier.")
Pour cela, un panel d’édition s’ouvre dans la partie affichage.  
On rentre le nom, l’extension et le contenu du fichier. Le chemin du fichier est automatiquement formé avec le dossier sélectionné et le nom + extension du fichier.  
Si le fichier ajouté existe déjà dans le dossier sélectionné, il n’est pas créé et un message est affiché à l’utilisateur.
![6 : Remplissage des informations du fichier à ajouter.](../screenshots/screenshots/6.png?raw=true "Remplissage des informations du fichier à ajouter.")
Le fichier apparait dans le dossier sélectionné.
![7 : Affichage du fichier ajouté.](../screenshots/screenshots/7.png?raw=true "Affichage du fichier ajouté.")

---

## Suppression d’un fichier du dossier sélectionné :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier à supprimer dans la liste des fichiers du dossier.  
Puis, on clique sur le bouton Supprimer Fichier.
![8 : Sélection du dossier puis du fichier à supprimer.](../screenshots/screenshots/8.png?raw=true "Sélection du dossier puis du fichier à supprimer.")

---

## Modification du nom, de l’extension ou du contenu d’un fichier du dossier sélectionné
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier à modifier dans la liste des fichiers du dossier.  
Puis, on clique sur le bouton Modifier Fichier.
![9 : Sélection du dossier puis du fichier à modifier.](../screenshots/screenshots/9.png?raw=true "Sélection du dossier puis du fichier à modifier.")
Le panel d’édition s’ouvre présentant les informations du fichier.  
On modifie le nom ou l’extension et on clique sur le premier bouton sauvegarder ou on modifie le contenu et on clique sur le deuxième bouton sauvegarder.  
![10 : Modification du fichier.](../screenshots/screenshots/10.png?raw=true "Modification du fichier.")
Si le fichier avec un nouveau nom existe déjà dans le dossier sélectionné, il n’est pas créé et un message est affiché à l’utilisateur.
![11 : Affichage du fichier modifié.](../screenshots/screenshots/11.png?raw=true "Affichage du fichier modifié.")

---

## Duplication d’un fichier du dossier sélectionné :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier à dupliquer dans la liste des fichiers du dossier.  
Puis, on clique sur le bouton Dupliquer Fichier. Par défaut, le fichier dupliqué porte le même nom que le fichier originel suivi de « _Copie ».  
Si le fichier dupliqué existe déjà dans le dossier sélectionné, il n’est pas créé et un message est affiché à l’utilisateur.
![12 : Sélection du dossier puis du fichier à dupliquer.](../screenshots/screenshots/12.png?raw=true "Sélection du dossier puis du fichier à dupliquer.")

---

## Chiffrement d’un fichier du dossier sélectionné :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier à chiffrer dans la liste des fichiers du dossier.  
Puis, on clique sur le bouton Chiffrer Fichier. Par défaut, le fichier chiffré porte le même nom que le fichier originel suivi de « _Chiffre ».  
L’algorithme de chiffrement est AES avec une clé unique spécifiée dans le code de l’application.  
Si le fichier chiffré existe déjà dans le dossier sélectionné, il n’est pas créé et un message est affiché à l’utilisateur.
![13 : Sélection du dossier puis du fichier à chiffrer.](../screenshots/screenshots/13.png?raw=true "Sélection du dossier puis du fichier à chiffrer.")

---

## Déchiffrement d’un fichier du dossier sélectionné :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier à déchiffrer dans la liste des fichiers du dossier.  
Puis, on clique sur le bouton Déchiffrer Fichier. Par défaut, le fichier déchiffré porte le même nom que le fichier originel suivi de « _Dechiffre ».  
Si le fichier déchiffré existe déjà dans le dossier sélectionné, il n’est pas créé et un message est affiché à l’utilisateur.
![14 : Sélection du dossier puis du fichier à déchiffrer.](../screenshots/screenshots/14.png?raw=true "Sélection du dossier puis du fichier à déchiffrer.")

---

## Concaténation des fichiers du dossier sélectionné entre eux avec des fichiers extérieurs :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
On sélectionne ensuite le fichier ou les fichiers à concaténer dans la liste des fichiers du dossier.  
Puis, on clique sur Concaténer Fichier.  
![15 : Sélection du dossier puis des fichiers à concaténer.](../screenshots/screenshots/15.png?raw=true "Sélection du dossier puis des fichiers à concaténer.")
Un panel de concaténation de fichier s’ouvre.  
Pour ajouter un fichier ne provenant pas du dossier sélectionné, on clique sur Ajouter des fichiers ne provenant pas du dossier sélectionné.  
Une fenêtre de sélection de fichiers s’ouvre pour sélectionner le fichier à concaténer.  
![16 : Ajout des fichiers ne provenant pas du dossier sélectionné.](../screenshots/screenshots/16.png?raw=true "Ajout des fichiers ne provenant pas du dossier sélectionné.")
On peut également modifier l’ordre des fichiers à concaténer avec les boutons Haut et Bas. Le contenu du fichier le plus en haut deviendra le début du fichier concaténé et le fichier le plus en bas deviendra la fin du fichier.  
![17 : Modification de l’ordre de concaténation des fichiers.](../screenshots/screenshots/17.png?raw=true "Modification de l’ordre de concaténation des fichiers.")
Puis, on clique sur le bouton Concaténer. Par défaut, le fichier concaténé porte le même nom que le fichier le plus en haut suivi de « _Concaténé ».  
![18 : Affichage du fichier concaténé.](../screenshots/screenshots/18.png?raw=true "Affichage du fichier concaténé.")

---

## Listage de toutes les occurrences d’un mot dans tous les fichiers du dossier sélectionné avec le numéro de la ligne où le mot apparait associé au nombre de fois où le mot apparait dans cette ligne :
On commence par sélectionner un dossier pour lister les fichiers de celui-ci dans la partie affichage.  
Puis, on clique sur Lister les occurrences d’un mot.  
![19 : Sélection du dossier pour lister les occurrences d’un mot dans tous ses fichiers.](../screenshots/screenshots/19.png?raw=true "Sélection du dossier pour lister les occurrences d’un mot dans tous ses fichiers.")
Un panel de recherche de mot s’ouvre. Celui-ci présente la liste des fichiers du dossier sélectionné avec une zone de texte pour saisir le mot à rechercher.  
On sait le mot puis on clique sur Lister.  
Si le mot est présent, un texte est affiché à côté de chaque fichier :   
« A la ligne X, le mot apparait Y fois. ».
![20 : Visualisation des couples numéro de lignes/nombre d’apparitions pour chaque fichier.](../screenshots/screenshots/20.png?raw=true "Visualisation des couples numéro de lignes/nombre d’apparitions pour chaque fichier.")
Pour rechercher un nouveau mot, on entre un autre mot dans la zone de saisie et on clique une nouvelle fois sur Lister. L’affichage est mis à jour.
