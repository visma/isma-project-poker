A duel to the death between two brothers

Scenario: duel to the death between 2 players with folds before hands deal

Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : FOLD()
Then mario est vainqueur sans rien montrer gagne 15 jetons

Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then luigi est au bouton
When mario paie la petite blinde
When luigi paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When mario effectue l'action : FOLD()
Then luigi est vainqueur sans rien montrer gagne 15 jetons

Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : FOLD()
Then mario est vainqueur sans rien montrer gagne 15 jetons

Then mario a 105 jetons
Then luigi a 95 jetons

Scenario: duel to the death between 2 players with folds before flop deal

Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : CALL()
When mario effectue l'action : CHECK()

Then etape en cours : BETS_2 (pot : 20)
When luigi effectue l'action : FOLD()

Then mario est vainqueur sans rien montrer gagne 20 jetons

Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then luigi est au bouton
When mario paie la petite blinde
When luigi paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When mario effectue l'action : CALL()
When luigi effectue l'action : CHECK()

Then etape en cours : BETS_2 (pot : 20)
When mario effectue l'action : FOLD()

Then luigi est vainqueur sans rien montrer gagne 20 jetons

Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : CALL()
When mario effectue l'action : CHECK()

Then etape en cours : BETS_2 (pot : 20)
When luigi effectue l'action : FOLD()

Then mario est vainqueur sans rien montrer gagne 20 jetons
Then mario a 110 jetons
Then luigi a 90 jetons
