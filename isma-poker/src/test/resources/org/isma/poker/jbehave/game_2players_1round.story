A duel to the death between two brothers

Scenario: duel to the death

Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given les prochaines cartes du deck sont : 7-D, 10-H, 2-S, 9-H, 10-S, Q-D, A-H, 7-S, 7-H
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)

Then mario est au bouton
Then luigi paie la petite blinde
Then mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)
Then mario reçoit les cartes cachees : 7-D, 2-S
Then luigi reçoit les cartes cachees : 10-H, 9-H

When luigi effectue l'action : CALL()

Then etape en cours : BETS_1 (pot : 20)
Then mario est le prochain joueur à parler

When mario effectue l'action : CHECK()

Then etape en cours : BETS_2 (pot : 20)
Then le croupier distribue le flop : 10-S, Q-D, A-H

When luigi effectue l'action : BET(10)
When mario effectue l'action : CALL()

Then etape en cours : BETS_3 (pot : 40)
Then le croupier distribue le turn : 10-S, Q-D, A-H, 7-S

When luigi effectue l'action : BET(10)
When mario effectue l'action : CALL()

Then etape en cours : BETS_4 (pot : 60)
Then le croupier distribue la river : 10-S, Q-D, A-H, 7-S, 7-H

When luigi effectue l'action : BET(10)
When mario effectue l'action : RAISE(20)
When luigi effectue l'action : CALL()

Then etape en cours : SHOWDOWN (pot : 120)

When luigi montre TWO_PAIR : 10-S, 10-H, 7-S, 7-H, A-H
When mario montre THREE_OF_A_KIND : 7-S, 7-H, 7-D, A-H, Q-D
Then mario est vainqueur avec THREE_OF_A_KIND et gagne 120 jetons
Then luigi a perdu avec TWO_PAIR