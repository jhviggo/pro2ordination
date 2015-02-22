package storage;

import java.util.ArrayList;
import java.util.List;

import ordination.Laegemiddel;
import ordination.Patient;

public class Storage {
	private static List<Patient> patienter = new ArrayList<Patient>();
	private static List<Laegemiddel> laegemidler = new ArrayList<Laegemiddel>();

	/**
	 * Returnerer en liste med alle gemte patienter
	 */
	public static List<Patient> getAllPatienter() {
		return new ArrayList<Patient>(patienter);
	}

	/**
	 * Gemmer patient
	 */
	public static void gemPatient(Patient patient) {
		if (!patienter.contains(patient)) {
			patienter.add(patient);
		}
	}

	/**
	 * Returnerer en liste med alle gemte lægemidler
	 */
	public static List<Laegemiddel> getAllLaegemidler() {
		return new ArrayList<Laegemiddel>(laegemidler);
	}

	/**
	 * Gemmer lægemiddel 
	 */
	public static void gemLaegemiddel(Laegemiddel laegemiddel) {
		if (!laegemidler.contains(laegemiddel)) {
			laegemidler.add(laegemiddel);
		}
	}

}
