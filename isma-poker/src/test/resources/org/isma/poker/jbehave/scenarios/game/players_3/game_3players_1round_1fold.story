A duel to the death between 3 opponents

Scenario: duel to the death between 3 players with a fold and check on player hands

Given la table de jeu est vide, la partie commencera lorsque 3 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given les prochaines cartes du deck sont : 7-D, 10-H, A-C, 2-S, 9-H, A-S, 10-S, Q-D, A-H, 7-S, 7-H
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie
Given le joueur peach dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When peach paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)
Then mario reçoit les cartes cachees : 7-D, 2-S
Then luigi reçoit les cartes cachees : 10-H, 9-H
Then peach reçoit les cartes cachees : A-C, A-S
Then mario a 2 carte(s) en main
Then luigi a 2 carte(s) en main
Then peach a 2 carte(s) en main

When mario effectue l'action : CALL()
When luigi effectue l'action : FOLD()
When peach effectue l'action : CHECK()
Then mario a 5 carte(s) en main
Then luigi a 0 carte(s) en main
Then peach a 5 carte(s) en main
