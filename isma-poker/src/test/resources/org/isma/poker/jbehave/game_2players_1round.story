A duel to the death between two brothers

Scenario: duel to the death

Given la table de jeu est vide
And les blindes sont à 5/10 jetons
And les prochaines cartes du deck sont : 7-D, 10-H, 2-S, 9-H, 10-S, Q-D, A-H, 7-S, 7-H
And le joueur mario dispose de 100 jetons et rentre dans la partie
And le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BETS_1 (pot : 15)
And mario est au bouton
And luigi paie la petite blinde
And mario paie la grosse blinde
And mario reçoit les cartes cachees : 7-D, 2-S
And luigi reçoit les cartes cachees : 10-H, 9-H

When luigi effectue l'action : CALL()
Then etape en cours : BETS_1 (pot : 20)
And mario est le prochain joueur à parler

When mario effectue l'action : CHECK()
Then etape en cours : BETS_2 (pot : 20)
And le croupier distribue le flop : 10-S, Q-D, A-H

When luigi effectue l'action : BET(10)
And mario effectue l'action : CALL()
Then etape en cours : BETS_3 (pot : 40)
Then le croupier distribue le turn : 10-S, Q-D, A-H, 7-S

When luigi effectue l'action : BET(10)
And mario effectue l'action : CALL()
Then etape en cours : BETS_4 (pot : 60)
And le croupier distribue la river : 10-S, Q-D, A-H, 7-S, 7-H

When luigi effectue l'action : BET(10)
And mario effectue l'action : RAISE(20)
And luigi effectue l'action : CALL()
Then etape en cours : SHOWDOWN (pot : 120)

When luigi montre TWO_PAIR : 10-S, 10-H, 7-S, 7-H, A-H
And mario montre THREE_OF_A_KIND : 7-S, 7-H, 7-D, A-H, Q-D
Then mario est vainqueur avec THREE_OF_A_KIND et gagne 120 jetons
And luigi a perdu avec TWO_PAIR