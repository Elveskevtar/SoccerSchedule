package com.soccer;

import java.util.ArrayList;
import java.util.Collections;

public class Driver {

	private ArrayList<Team> template;

	public static void main(String[] args) {
		new Driver();
	}

	@SuppressWarnings("unused")
	public Driver() {
		template = new ArrayList<Team>();
		for (Team team : Team.values()) {
			template.add(team);
		}
		for (Team team : template) {
			team.populateList();
		}
		Date[] batch1 = createDates(false, 0, 4);
		Date[] ext1 = createDates(true, 5, 5);
		Date[] batch2 = createDates(false, 6, 10);
		Date[] ext2 = createDates(true, 11, 11);
		Date[] batch3 = createDates(false, 12, 17);
	}

	public Date[] createDates(boolean ext, int start, int end) {
		boolean flag = true;
		Date[] dates = new Date[end - start + 1];
		while (flag) {
			for (int i = start; i <= end; i++) {
				ArrayList<Team> teams = new ArrayList<Team>();
				for (int j = 0; j < template.size(); j++) {
					teams.add(template.get(j));
				}
				Game game1 = null;
				Game game2 = null;
				Game game3 = null;
				Game game4 = null;
				game1 = createGame(teams);
				game2 = createGame(teams);
				game3 = createGame(teams);
				game4 = createGame(teams);
				Game game5 = null;
				if (!ext)
					game5 = createGame(teams);
				dates[i - start] = new Date(i, game1, game2, game3, game4,
						game5);
				if (game1 != null && game2 != null && game3 != null
						&& game4 != null && (ext || game5 != null)) {
					if (i == end)
						flag = false;
					else
						continue;
				} else {
					break;
				}
			}
		}
		for (int i = 0; i < dates.length; i++) {
			System.out.println("DAY " + (start + i + 1));
			System.out.println(dates[i].getGame1().getTeam1().getTeamName()
					+ " v. " + dates[i].getGame1().getTeam2().getTeamName());
			System.out.println(dates[i].getGame2().getTeam1().getTeamName()
					+ " v. " + dates[i].getGame2().getTeam2().getTeamName());
			System.out.println(dates[i].getGame3().getTeam1().getTeamName()
					+ " v. " + dates[i].getGame3().getTeam2().getTeamName());
			System.out.println(dates[i].getGame4().getTeam1().getTeamName()
					+ " v. " + dates[i].getGame4().getTeam2().getTeamName());
			if (!ext)
				System.out
						.println(dates[i].getGame5().getTeam1().getTeamName()
								+ " v. "
								+ dates[i].getGame5().getTeam2().getTeamName());
		}
		return dates;
	}

	public Game createGame(ArrayList<Team> teams) {
		// System.out.println("START: " + teams.size());
		Collections.shuffle(teams);
		if (teams.get(0).getTeamsHaventPlayed().size() == 0) {
			teams.get(0).populateList();
			/*
			 * for (Team team : template) if
			 * (!team.getTeamName().equals(teams.get(0).getTeamName()))
			 * team.getTeamsHaventPlayed().add(teams.get(0));
			 */
		}
		int breaker = 0;
		while (!crossReferenceTeamList(teams, teams.get(0)
				.getTeamsHaventPlayed())
				|| !crossReferenceTeamList(teams.get(0).getTeamsHaventPlayed(),
						teams.get(0).getTeamsHaventPlayed().get(0)
								.getTeamsHaventPlayed())) {
			Collections.shuffle(teams);
			for (Team team : teams) {
			}
			if (teams.get(0).getTeamsHaventPlayed().size() == 0) {
				teams.get(0).populateList();
				/*
				 * for (Team team : template) if
				 * (!team.getTeamName().equals(teams.get(0).getTeamName()))
				 * team.getTeamsHaventPlayed().add(teams.get(0));
				 */
			}
			breaker++;
			if (breaker == 100)
				return null;
		}
		Team team1 = teams.get(0);
		teams.remove(0);
		int z = 0;
		breaker = 0;
		do {
			Collections.shuffle(team1.getTeamsHaventPlayed());
			for (int i = 0; i < teams.size(); i++) {
				for (int j = 0; j < team1.getTeamsHaventPlayed().size(); j++) {
					if (teams
							.get(i)
							.getTeamName()
							.equals(team1.getTeamsHaventPlayed().get(j)
									.getTeamName())) {
						z = j;
						break;
					}
				}
			}
			breaker++;
			if (breaker == 100)
				return null;
		} while (!crossReferenceTeamList(team1.getTeamsHaventPlayed(), team1
				.getTeamsHaventPlayed().get(z).getTeamsHaventPlayed()));
		Team team2 = team1.getTeamsHaventPlayed().get(z);
		for (int j = 0; j < teams.size(); j++) {
			if (teams.get(j).getTeamName().equals(team2.getTeamName())) {
				// System.out.println("CHECKPOINT");
				teams.remove(j);
				break;
			}
		}
		removeTeamFromList(team1, team2);
		removeTeamFromList(team2, team1);
		/*if (team1.getTeamName().equals("Grand Blanc")
				|| team2.getTeamName().equals("Grand Blanc"))
			System.out.println(team1.getTeamName() + " "
					+ team1.getTeamsHaventPlayed().size() + " : "
					+ team2.getTeamName() + " "
					+ team2.getTeamsHaventPlayed().size());*/
		// System.out.println("END: " + teams.size());
		return new Game(team1, team2);
	}

	public int teamWithOneMoreLeft(ArrayList<Team> teams) {
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getTeamsHaventPlayed().size() == 1)
				return i;
		}
		return -1;
	}

	public boolean crossReferenceTeamList(ArrayList<Team> referenceList,
			ArrayList<Team> teamList) {
		for (int i = 0; i < referenceList.size(); i++) {
			for (int j = 0; j < teamList.size(); j++) {
				if (referenceList.get(i).getTeamName()
						.equals(teamList.get(j).getTeamName())) {
					return true;
				}
			}
		}
		return false;
	}

	public void removeTeamFromList(Team teamA, Team teamB) {
		for (int i = 0; i < teamA.getTeamsHaventPlayed().size(); i++) {
			if (!teamA.getTeamsHaventPlayed().get(i).getTeamName()
					.equals(teamB.getTeamName())) {
				continue;
			} else {
				teamA.getTeamsHaventPlayed().remove(i);
				break;
			}
		}
	}

	public enum Team {
		ONE("Grand Blanc"), TWO("WL Northern"), THREE("Canton"), FOUR(
				"WL Western"), FIVE("Brighton"), SIX("Plymouth"), SEVEN(
				"Northville"), EIGHT("Salem"), NINE("Saline"), TEN("WL Central"), ELEVEN(
				"Dearborn");

		private String teamName;

		private ArrayList<Team> teamsHaventPlayed;

		Team(String teamName) {
			this.teamName = teamName;
		}

		public String getTeamName() {
			return teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public ArrayList<Team> getTeamsHaventPlayed() {
			return teamsHaventPlayed;
		}

		public void setTeamsHaventPlayed(ArrayList<Team> teamsHaventPlayed) {
			this.teamsHaventPlayed = teamsHaventPlayed;
		}

		public void populateList() {
			teamsHaventPlayed = new ArrayList<Team>();
			for (Team team : Team.values())
				if (!team.getTeamName().equals(teamName))
					teamsHaventPlayed.add(team);
		}
	}
}
