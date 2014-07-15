package com.ankit.funnyboat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussianFactory;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationVectorReader;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;

public class TestGesture {
	OpdfMultiGaussianFactory initFactoryPunch = null;
	Reader learnReaderPunch = null;
	List<List<ObservationVector>> learnSequencesPunch = null;
	KMeansLearner<ObservationVector> kMeansLearnerPunch = null;
	Hmm<ObservationVector> initHmmPunch = null;
	Hmm<ObservationVector> learntHmmPunch = null;
	Hmm<ObservationVector> learntHmmScrolldown = null;
	Hmm<ObservationVector> learntHmmSend = null;
	String root = Environment.getExternalStorageDirectory().toString();
	File myDir = new File(root + "/Data");

	public void train() {
		myDir.mkdirs();

		Log.d("RAKESH", "in TEstGesture train method");

		// Create HMM for punch gesture
		Boolean exception = false;
		int x = 10;
		while (!exception) {
			try {
				OpdfMultiGaussianFactory initFactoryPunch = new OpdfMultiGaussianFactory(
						3);
				Log.d("RAKESH", "in TEstGesture train method before read boat");

				Reader learnReaderPunch = new FileReader(new File(myDir,
						"boat.seq"));
				Log.d("RAKESH", "in TEstGesture train method after read boat");
				List<List<ObservationVector>> learnSequencesPunch = ObservationSequencesReader
						.readSequences(new ObservationVectorReader(),
								learnReaderPunch);
				learnReaderPunch.close();
				Log.d("RAKESH",
						"in TEstGesture train method learnreader punch closed");

				KMeansLearner<ObservationVector> kMeansLearnerPunch = new KMeansLearner<ObservationVector>(
						x, initFactoryPunch, learnSequencesPunch);
				Log.d("RAKESH",
						"in TEstGesture train method kMeansLearnerPunch");
				// Create an estimation of the HMM (initHmm) using one iteration
				// of the
				// k-Means algorithm
				Hmm<ObservationVector> initHmmPunch = kMeansLearnerPunch
						.iterate();
				Log.d("RAKESH", "in TEstGesture train method initHmmPunch");
				// Use BaumWelchLearner to create the HMM (learntHmm) from
				// initHmm

				BaumWelchLearner baumWelchLearnerPunch = new BaumWelchLearner();
				Log.d("RAKESH",
						"in TEstGesture train method baumWelchLearnerPunch");

				//uncomment this step this.learntHmmPunch =  baumWelchLearnerPunch.learn(initHmmPunch, learnSequencesPunch);

				Log.d("RAKESH", "in TEstGesture train method learntHmmPunch");
				exception = true;
				Log.d("RAKESH",
						"in TEstGesture train method boat try completed");
			} catch (Exception e) {
				x--;
				// System.out.println(x);
			}
		}
		// Create HMM for scroll-down gesture
		Boolean exception1 = false;
		int x1 = 10;
		while (!exception1) {
			try {
				OpdfMultiGaussianFactory initFactoryScrolldown = new OpdfMultiGaussianFactory(
						3);

				Reader learnReaderScrolldown = new FileReader(new File(myDir,
						"LeftToRight.seq"));
				List<List<ObservationVector>> learnSequencesScrolldown = ObservationSequencesReader
						.readSequences(new ObservationVectorReader(),
								learnReaderScrolldown);
				learnReaderScrolldown.close();

				KMeansLearner<ObservationVector> kMeansLearnerScrolldown = new KMeansLearner<ObservationVector>(
						x1, initFactoryScrolldown, learnSequencesScrolldown);
				// Create an estimation of the HMM (initHmm) using one iteration
				// of the
				// k-Means algorithm
				Hmm<ObservationVector> initHmmScrolldown = kMeansLearnerScrolldown
						.iterate();

				// Use BaumWelchLearner to create the HMM (learntHmm) from
				// initHmm
				BaumWelchLearner baumWelchLearnerScrolldown = new BaumWelchLearner();
				
				//uncomment this step this.learntHmmScrolldown = baumWelchLearnerScrolldown.learn( initHmmScrolldown,learnSequencesScrolldown);
				exception1 = true;
				// System.out.println("here1");
				Log.d("RAKESH",
						"in TEstGesture train method LtoR try completed");
			} catch (Exception e) {
				x1--;
				// System.out.println(x1);

			}
		}
		// Create HMM for send gesture
		Boolean exception2 = false;
		int x2 = 10;
		while (!exception2) {
			try {
				OpdfMultiGaussianFactory initFactorySend = new OpdfMultiGaussianFactory(
						3);

				Reader learnReaderSend = new FileReader(new File(myDir,
						"RightToLeft.seq"));
				List<List<ObservationVector>> learnSequencesSend = ObservationSequencesReader
						.readSequences(new ObservationVectorReader(),
								learnReaderSend);
				learnReaderSend.close();

				KMeansLearner<ObservationVector> kMeansLearnerSend = new KMeansLearner<ObservationVector>(
						x2, initFactorySend, learnSequencesSend);
				// Create an estimation of the HMM (initHmm) using one iteration
				// of the
				// k-Means algorithm
				Hmm<ObservationVector> initHmmSend = kMeansLearnerSend
						.iterate();

				// Use BaumWelchLearner to create the HMM (learntHmm) from
				// initHmm
				BaumWelchLearner baumWelchLearnerSend = new BaumWelchLearner();
				//uncomment this step this.learntHmmSend = baumWelchLearnerSend.learn(initHmmSend, learnSequencesSend);
				exception2 = true;
				Log.d("RAKESH",
						"in TEstGesture train method RtoL try completed");
			} catch (Exception e) {
				x2--;
				// System.out.println(x2);
			}
		}
	}

	public String test(File seqfilename) throws Exception {
		Log.i("RAKESH", "Test1");
		Reader testReader = new FileReader(seqfilename);
		Log.i("RAKESH", "Test2");
		List<List<ObservationVector>> testSequences = ObservationSequencesReader
				.readSequences(new ObservationVectorReader(), testReader);
		Log.i("RAKESH", "Test3");
		testReader.close();
		Log.i("RAKESH", "Test4");
		short gesture=0; // punch = 1, scrolldown = 2, send = 3
		double punchProbability=0.0, scrolldownProbability=0.0, sendProbability = 0.0;
		for (int i = 0; i < testSequences.size(); i++) {
			Log.i("RAKESH", "Testfor " + i);
			//uncomment this step punchProbability = this.learntHmmPunch.probability(testSequences.get(i));
			Log.i("RAKESH", "Testfor1 " + i);
			// System.out.println(this.learntHmmPunch.probability(testSequences.get(i)));
			gesture = 1;
			// System.out.println(this.learntHmmScrolldown);
			//uncomment this step scrolldownProbability = this.learntHmmScrolldown.probability(testSequences.get(i));
			Log.i("RAKESH", "Testfor2 " + i);
			if (scrolldownProbability > punchProbability) {
				gesture = 2;
				Log.i("RAKESH", "Testfor3 " + i);
			}

			//uncomment this step sendProbability = this.learntHmmSend.probability(testSequences.get(i));
		}
		// System.out.println(punchProbability +","+scrolldownProbability
		// +","+sendProbability);
		if ((gesture == 1 && sendProbability > punchProbability)
				|| (gesture == 2 && sendProbability > scrolldownProbability)) {
			gesture = 3;
		}
		Log.i("RAKESH", punchProbability + "   " + sendProbability + "   "
				+ scrolldownProbability);
		if (gesture == 1) {
			System.out.println("This is a punch gesture");
			return "stomp";
		} else if (gesture == 2) {
			System.out.println("This is a right-left gesture");
			return "stomp";
		} else if (gesture == 3) {
			System.out.println("This is a left to right gesture");
			return "stomp";
		} else {
			return "stomp";
		}
	}
}