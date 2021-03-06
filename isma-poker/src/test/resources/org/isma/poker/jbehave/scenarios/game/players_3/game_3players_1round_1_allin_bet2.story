A duel to the death between 3 opponents

Scenario: duel to the death between 3 players with 1 allin on bet 2

Given la table de jeu est vide, la partie commencera lorsque 3 joueurs sont presents
Given les blindes sont � 5/10 jetons
Given les prochaines cartes du deck sont : 7-D, 10-H, A-C, 2-S, 9-H, A-S, 10-S, Q-D, A-H, 7-S, 7-H
Given le joueur mario dispose de 200 jetons et rentre dans la partie
Given le joueur luigi dispose de 200 jetons et rentre dans la partie
Given le joueur peach dispose de 100 jetons et rentre dans la partie

When la partie d�marre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
When luigi paie la petite blinde
When peach paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)
Then mario re�oit les cartes cachees : 7-D, 2-S
Then luigi re�oit les cartes cachees : 10-H, 9-H
Then peach re�oit les cartes cachees : A-C, A-S

When mario effectue l'action : CALL()
When luigi effectue l'action : CALL()
When peach effectue l'action : RAISE(10)
When mario effectue l'action : CALL()
When luigi effectue l'action : CALL()

Then etape en cours : BETS_2 (pot : 60)
Then le croupier distribue le flop : 10-S, Q-D, A-H

When mario effectue l'action : BET(10)
Then la mise en cours de mario est de 10 jetons
Then le montant des encheres en cours sur ce tour de mise est de 10 jetons

When luigi effectue l'action : CALL()
Then la mise en cours de mario est de 10 jetons
Then le montant des encheres en cours sur ce tour de mise est de 10 jetons

When peach effectue l'action : ALLIN()
Then la mise en cours de peach est de 80 jetons
Then le montant des encheres en cours sur ce tour de mise est de 80 jetons

When mario effectue l'action : CALL()
When luigi effectue l'action : CALL()

Then mario a 100 jetons
Then luigi a 100 jetons
Then peach a 0 jetons
Then etape en cours : BETS_3 (pot : 300)
Then le croupier distribue le turn : 10-S, Q-D, A-H, 7-S

When mario effectue l'action : CHECK()
When luigi effectue l'action : CHECK()

Then etape en cours : BETS_4 (pot : 300)
Then le croupier distribue la river : 10-S, Q-D, A-H, 7-S, 7-H

When mario effectue l'action : CHECK()
When luigi effectue l'action : CHECK()

Then etape en cours : SHOWDOWN (pot : 300)

When mario montre THREE_OF_A_KIND : 7-S, 7-H, 7-D, A-H, Q-D
When luigi montre TWO_PAIR : 10-S, 10-H, 7-S, 7-H, A-H
When peach montre FULL_HOUSE : A-S, A-H, A-C, 7-S, 7-H

Then peach est vainqueur avec FULL_HOUSE et gagne 300 jetons
Then mario a perdu avec THREE_OF_A_KIND
Then luigi a perdu avec TWO_PAIR