A duel to the death between two brothers


Scenario: duel to the death between 2 players in 2 rounds
Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given les prochaines cartes du deck sont : 7-D, 10-H, 2-S, 9-H, 10-S, Q-D, A-H, 7-S, 7-H
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then mario est au bouton
Then luigi paie la petite blinde
Then mario paie la grosse blinde
Then luigi reçoit les cartes cachees : 7-D, 2-S
Then mario reçoit les cartes cachees : 10-H, 9-H

When luigi effectue l'action : CALL()
When mario effectue l'action : CHECK()

Then le croupier distribue le flop : 10-S, Q-D, A-H
When luigi effectue l'action : BET(10)
When mario effectue l'action : CALL()

Then le croupier distribue le turn : 10-S, Q-D, A-H, 7-S
When luigi effectue l'action : BET(10)
When mario effectue l'action : CALL()

Then le croupier distribue la river : 10-S, Q-D, A-H, 7-S, 7-H
When luigi effectue l'action : BET(10)
When mario effectue l'action : RAISE(20)
When luigi effectue l'action : CALL()

When luigi montre THREE_OF_A_KIND : 7-S, 7-H, 7-D, A-H, Q-D
When mario montre TWO_PAIR : 10-S, 10-H, 7-S, 7-H, A-H
Then luigi est vainqueur avec THREE_OF_A_KIND et gagne 120 jetons
Then mario a perdu avec TWO_PAIR
Then luigi a 160 jetons
Then mario a 40 jetons

Given les prochaines cartes du deck sont : 2-H, A-C, 3-H, A-S, 4-H, 5-H, 6-H, 7-H, 8-H
Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then luigi est au bouton
Then mario paie la petite blinde
Then luigi paie la grosse blinde
Then mario reçoit les cartes cachees : 2-H, 3-H
Then luigi reçoit les cartes cachees : A-C, A-S

Then etape en cours : BETS_1 (pot : 15)
When mario effectue l'action : CALL()
When luigi effectue l'action : CHECK()

Then etape en cours : BETS_2 (pot : 20)
Then le croupier distribue le flop : 4-H, 5-H, 6-H
When mario effectue l'action : CHECK()
When luigi effectue l'action : CHECK()

Then etape en cours : BETS_3 (pot : 20)
Then le croupier distribue le turn : 4-H, 5-H, 6-H, 7-H
When mario effectue l'action : CHECK()
When luigi effectue l'action : CHECK()

Then etape en cours : BETS_4 (pot : 20)
Then le croupier distribue la river : 4-H, 5-H, 6-H, 7-H, 8-H
When mario effectue l'action : CHECK()
When luigi effectue l'action : CHECK()

Then etape en cours : SHOWDOWN (pot : 20)
When mario montre STRAIGHT_FLUSH : 8-H, 7-H, 6-H, 5-H, 4-H
When luigi montre STRAIGHT_FLUSH : 8-H, 7-H, 6-H, 5-H, 4-H
Then mario est vainqueur avec STRAIGHT_FLUSH et gagne 10 jetons
Then luigi est vainqueur avec STRAIGHT_FLUSH et gagne 10 jetons

Given les prochaines cartes du deck sont : A-H, K-H, Q-H, J-H, 10-H, 9-H, 8-H, 7-H, 8-H
Given un nouveau round va démarrer

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton
Then luigi paie la petite blinde
Then mario paie la grosse blinde
Then luigi reçoit les cartes cachees : A-H, Q-H
Then mario reçoit les cartes cachees : K-H, J-H

Then etape en cours : BETS_1 (pot : 15)
When luigi effectue l'action : CALL()
When mario effectue l'action : CHECK()