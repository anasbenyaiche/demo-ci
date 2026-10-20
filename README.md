# demo-ci

Projet fil rouge de la formation **Intégration continue : construire, publier, vérifier**
(formateur : Anas Ben Yaiche).

Une petite bibliothèque Java de calcul de prix (TVA, remise). Le code est volontairement
simple : l'objet de la formation est la chaîne d'intégration autour de lui
(Maven, Nexus, Jenkins, SonarQube).

## Démarrer

```bash
git clone -o formateur <URL-du-depot> demo-ci
cd demo-ci
```

Suivez ensuite le **cahier des ateliers**, atelier 1.

## Branches

| Branche | Contenu |
|---|---|
| `main` | Point de départ de l'atelier 1 : code fourni, POM et Jenkinsfile à compléter |
| `solution/atelier-1` | Build Maven complet : tests, JaCoCo, profils, Maven Wrapper |
| `solution/atelier-2` | + publication Nexus (`distributionManagement`) |
| `solution/atelier-3` | + pipeline Jenkins (`Jenkinsfile`, `ci-settings.xml`) |
| `solution/atelier-4` | + analyse SonarQube et Quality Gate |

## Rattraper un atelier

Attention : cette commande remplace votre travail local par la solution.

```bash
git fetch formateur
git reset --hard formateur/solution/atelier-2   # remplacer 2 par le numéro voulu
```

## Prérequis

JDK 17 ou plus, Maven 3.9, Git, Docker (pour Jenkins, Nexus et SonarQube).
