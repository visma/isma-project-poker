A duel to the death between two brothers

Scenario: duel to the death between 2 players with folds

Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
Then luigi paie la petite blinde
Then mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : FOLD()
Then mario est vainqueur sans rien montrer gagne 15 jetons

Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then luigi est au bouton
Then mario paie la petite blinde
Then luigi paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When mario effectue l'action : FOLD()

Then luigi est vainqueur sans rien montrer gagne 15 jetons
