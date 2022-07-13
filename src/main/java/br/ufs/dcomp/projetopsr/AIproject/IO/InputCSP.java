package br.ufs.dcomp.projetopsr.AIproject.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.ufs.dcomp.projetopsr.AIproject.variables.StaffMember;

public class InputCSP {
	Integer startTime;
	Integer endTime;
	List<StaffMember> people = new ArrayList<>();

	public InputCSP() {
		Path currentWorkingDir = Paths.get("").toAbsolutePath();
		System.out.println(currentWorkingDir.normalize().toString());

		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			Integer currentStep = 0;
			while ((line = br.readLine()) != null) {
				if (currentStep == 0) {
					String[] hours = line.trim().split("\\s+");
					this.startTime = Integer.parseInt(hours[0]);
					this.endTime = Integer.parseInt(hours[1]);
					currentStep++;
					continue;
				} else if (currentStep == 1) {
					String[] data = line.trim().split("\\|");
					if (data[0].equals("*")) {
						currentStep++;
					} else {
						String memberName = data[0].trim();
						Integer memberHours = Integer.parseInt(data[1].trim());
						List<Integer> memberFreeHours = new ArrayList<>();
						for (String number : data[2].trim().split("\\s+")) {
							memberFreeHours.add(Integer.parseInt(number.trim()));
						}
						Boolean vaccinated = Boolean.parseBoolean(data[3].trim());
						this.addStaffMember(memberName, memberHours, memberFreeHours, vaccinated);
					}

				} else if (currentStep == 2) {
					String[] data = line.trim().split("\\s+");
					this.addDependecies(data[0], data[1]);

				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.printSchema();


	}

	public void printSchema() {
		System.out.println("Members can work from " + this.startTime + " to " + this.endTime);
		for (StaffMember person : this.people) {
			System.out.print("Name: " + person.getName() + " | ");
			System.out.print("Workload: " + person.getHour() + " | ");
			System.out.print("Free hours: ");
			Map<Integer, Boolean> free = person.getFree();
			for (int i = 0; i < 24; i++) {
				if (free.get(i) != null) {
					if (free.get(i)) {
						System.out.print(i + " ");
					}
				}
			}
			System.out.print("| ");
			if (person.isVaccinated()) {
				System.out.print("Vaccinated: YES");
			} else {
				System.out.print("Vaccinated: NO");
			}
			System.out.println();
		}
		for (StaffMember p1 : this.people) {
			for(StaffMember p2 : p1.getDependencies()) {
				System.out.println( p1.getName() + " depends on " + p2.getName() + " to work.");
			}
		}
		
	}

	private void addStaffMember(String staffName, Integer hours, List<Integer> free, Boolean vaccinated) {
		StaffMember newMember = new StaffMember(staffName, hours, free, vaccinated);
		List<StaffMember> people = this.people;
		people.add(newMember);
		this.people = people;
	}

	public void addDependecies(String name1, String name2) {
		Integer index1 = -1, index2 = -1;
		for (StaffMember p1 : this.people) {
			if (p1.getName().equals(name1)) {
				index1 = this.people.indexOf(p1);
			}
		}
		for (StaffMember p2 : this.people) {
			if (p2.getName().equals(name2)) {
				index2 = this.people.indexOf(p2);
			}
		}
		if (index1 != index2 && index1 != -1 && index2 != -1) {
			people.get(index1).addDependency(people.get(index2));
		}

	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public List<StaffMember> getPeople() {
		return people;
	}

	public void setPeople(List<StaffMember> people) {
		this.people = people;
	}

}
