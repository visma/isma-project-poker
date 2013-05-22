A duel to the death between 3 opponents

Scenario: 2 rounds - 3 players with sitout of bigblind on first round, checks beginning of 2nd round
Given la table de jeu est vide, la partie commencera lorsque 3 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie
Given le joueur peach dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi paie la petite blinde
When peach effectue l'action : SIT_OUT()
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When luigi effectue l'action : CALL()
When mario effectue l'action : CHECK()
Then etape en cours : BETS_2 (pot : 20)

When luigi effectue l'action : BET(10)
When mario effectue l'action : FOLD()

Then luigi a 110 jetons
Then mario a 90 jetons

Then etape en cours : BLINDS (pot : 0)
Then luigi est au bouton

When mario paie la petite blinde
When luigi paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

Scenario: 2 rounds - 3 players with sitout of smallblind on first round, checks beginning of 2nd round
Given la table de jeu est vide, la partie commencera lorsque 3 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie
Given le joueur peach dispose de 100 jetons et rentre dans la partie

When la partie démarre

Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi effectue l'action : SIT_OUT()
When peach paie la petite blinde
When mario paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)

When peach effectue l'action : CALL()
When mario effectue l'action : CHECK()
Then etape en cours : BETS_2 (pot : 20)

When peach effectue l'action : BET(10)
When mario effectue l'action : FOLD()

Then peach a 110 jetons
Then mario a 90 jetons

Then etape en cours : BLINDS (pot : 0)
Then peach est au bouton

When mario paie la petite blinde
When peach paie la grosse blinde
Then etape en cours : BETS_1 (pot : 15)