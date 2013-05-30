A duel to the death between two brothers

Scenario: 1v1 first bet : an allin and a call

Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given les prochaines cartes du deck sont : 7-D, 10-H, 2-S, 9-H, 10-S, Q-D, A-H, 7-S, 7-H
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi paie la petite blinde
Then la mise en cours de luigi est de 5 jetons
Then le montant des encheres en cours sur ce tour de mise est de 5 jetons

When mario paie la grosse blinde
Then la mise en cours de mario est de 10 jetons
Then le montant des encheres en cours sur ce tour de mise est de 10 jetons
Then etape en cours : BETS_1 (pot : 15)
Then luigi reçoit les cartes cachees : 7-D, 2-S
Then mario reçoit les cartes cachees : 10-H, 9-H

When luigi effectue l'action : ALLIN()
Then le montant des encheres en cours sur ce tour de mise est de 100 jetons
Then luigi a 0 jetons
Then etape en cours : BETS_1 (pot : 110)
Then mario est le prochain joueur à parler

When mario effectue l'action : ALLIN()
Then mario a 0 jetons
Then etape en cours : SHOWDOWN (pot : 200)

When luigi montre THREE_OF_A_KIND : 7-S, 7-H, 7-D, A-H, Q-D
When mario montre TWO_PAIR : 10-S, 10-H, 7-S, 7-H, A-H
Then luigi est vainqueur avec THREE_OF_A_KIND et gagne 200 jetons
Then mario a perdu avec TWO_PAIR
Then luigi a 200 jetons
Then mario a 0 jetons
Then etape en cours : END (pot : 0)