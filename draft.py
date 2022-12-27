import random
'''
	Source for probabilities on matches: https://projects.fivethirtyeight.com/2022-nfl-predictions/
	Source for tiebreakers and records: https://www.tankathon.com/nfl

	1. Get the existing records and tiebreaker, and probabilities for remaining games
	2. Simulate week 17
	3. Simulate week 18
	4. Figure out the draft order
	
	Steps 2-4 do NUM_SIMULATIONS times, then output the counts at each spot. For instance 70.1% of the time Houston is the #1 pick, etc.

	Limitation: Doesn't update SOS after results of final two weeks. 
'''
NUM_SIMULATIONS = 100000
GAMES_PLAYED = 15
TOTAL_GAMES = 17

# 1a Frequencies of draft picks in simulations for bottom-tier teams
# Key: 3-letter abbrevation, Value: Dict w/Key = draft spot, Value = Number of Occurrences
# Bottom-tier (eliminated and chance of top two pick): HOU, DEN, IND, ARI, CHI, LAR, ATL
# Currently all eliminated teams except CLE as they are currently at 11th pick which isn't really tanking (and don't even have their own pick)
results = {
	'HOU': {},
	'DEN': {},
	'IND': {},
	'ARI': {},
	'CHI': {},
	'LAR': {},
	'ATL': {}
}

# 1b Week 17
# Key: 3-letter abbrev of home team, Value: 3-letter abbrev of road team, home team odds
week17 = {
	'TEN': ['DAL', .21],
	'ATL': ['ARI', .60],
	'TBB': ['CAR', .55],
	'DET': ['CHI', .73],
	'WAS': ['CLE', .52],
	'KCY': ['DEN', .89],
	'NYG': ['IND', .63],
	'HOU': ['JAX', .28],
	'NEP': ['MIA', .63],
	'PHI': ['NOR', .77],
	'SEA': ['NYJ', .58],
	'LVR': ['SFO', .41],
	'LAC': ['LAR', .72],
	'GBP': ['MIN', .46],
	'BAL': ['PIT', .66],
	'CIN': ['BUF', .52]
}

# 1c Week 18
# Key: 3-letter abbrev of home team, Value: 3-letter abbrev of road team, home team odds
week18 = {
	'SFO': ['ARI', .85],
	'CIN': ['BAL', .65],
	'NOR': ['CAR', .54],
	'PIT': ['CLE', .55],
	'WAS': ['DAL', .28],
	'GBP': ['DET', .59],
	'IND': ['HOU', .72],
	'KCY': ['LVR', .28],
	'DEN': ['LAC', .37],
	'SEA': ['LAR', .52],
	'CHI': ['MIN', .27],
	'BUF': ['NEP', .81],
	'PHI': ['NYG', .81],
	'MIA': ['NYJ', .63],
	'ATL': ['TBB', .36],
	'JAX': ['TEN', .74]
}

for i in range(0, NUM_SIMULATIONS): # So we get a small CI
	if not i % 10000:
		print(i, 'SIMULATIONS COMPLETE')

	# 1d Records after GAMES_PLAYED games. Reset for each simulation of final TOTAL_GAMES - GAMES_PLAYED weeks
	# Key: 3-letter abbreviation, Value: [Wins, First 15 Game Strength of Schedule]
	# Ties counted as half a win.
	# In the case of traded picks, I list the original team
	records = {
		'HOU': [2.5, .49],
		'CHI': [3, .567],
		'DEN': [4, .49],
		'ARI': [4, .522],
		'IND': [4.5, .51],
		'ATL': [5, .465],
		'LAR': [5, .506],
		'CAR': [6, .457],
		'LVR': [6, .463],
		'NOR': [6, .502],
		'CLE': [6, .522],
		'SEA': [7, .461],
		'TEN': [7, .506],
		'NEP': [7, .516],
		'NYJ': [7, .529],
		'PIT': [7, .531],
		'GBP': [7, .537],
		'DET': [7, .541],
		'JAX': [7, .482],
		'TBB': [7, .490],
		'WAS': [7.5, .537],
		'MIA': [8, .539],
		'NYG': [8.5, .533],
		'LAC': [9, .435],
		'BAL': [10, .492],
		'SFO': [11, .414],
		'DAL': [11, .514],
		'CIN': [11, .525],
		'KCY': [12, .455],
		'MIN': [12, .480],
		'BUF': [12, .514],
		'PHI': [13, .475]
	}

	#2 Simulate Week 17
	for host in week17:
		home = week17[host]
		away, home_odds = home[0], home[1]
		home_list, away_list = records[host], records[away]
		rand = random.random() # 0 -> home_odds (exclusive) = home win, home_odds -> 1 = road win
		if rand < home_odds: # Home win, road lose, update totals
			home_list[0] += 1
		else:
			away_list[0] += 1

	#3 Simulate Week 18
	for host in week18:
		home = week18[host]
		away, home_odds = home[0], home[1]
		home_list, away_list = records[host], records[away]
		rand = random.random() # 0 -> home_odds (inclusive) = home win, home_odds -> 1 = road win
		if rand <= home_odds: # Home win
			home_list[0] += 1
		else: # Road win
			away_list[0] += 1

	#4 Draft Order
	# Not taking into account tiebreakers for playoff spots as only tanking teams are of interest
	draft_list = [] # Low to high, 3-letter abbrev
	for team in records:
		team_wins, team_sos = records[team][0], records[team][1]
		#print(team, team_wins, team_sos)
		for other in draft_list: 
			other_wins, other_sos = records[other][0], records[other][1]
			if team_wins < other_wins or (team_wins == other_wins and team_sos <= other_sos):
				# Insert BEFORE, and break
				indx = draft_list.index(other)
				draft_list.insert(indx, team)
				break
		if team not in draft_list: # List was empty or later pick than all teams currently in list
			draft_list.append(team)

	#5 Update the counts for our eliminated teams
	for team in results:
		pick = draft_list.index(team) + 1 # So 0th index becomes 1st pick, etc.
		history = results[team]
		if pick not in history:
			history[pick] = 1
		else:
			history[pick] += 1

# 6 Display the results
for team in results:
	for pick in results[team]:
		results[team][pick] = round(100 * results[team][pick]/NUM_SIMULATIONS, 1)
	print(team, results[team])