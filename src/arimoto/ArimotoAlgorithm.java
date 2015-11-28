/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimoto;

/**
 *
 * @author muslumoncel
 */
public class ArimotoAlgorithm {

	private static final Double[][] Pyc
		= {
			{0.9, 0.0, 0.0, 0.1},
			{0.1, 0.9, 0.0, 0.0},
			{0.0, 0.1, 0.9, 0.0},
			{0.0, 0.0, 0.9, 0.1}
		};
	private static final Double[] Pc = {0.25, 0.25, 0.25, 0.25};
	private static final Double[] Qy = new Double[4];
	private static final Double[] F = new Double[4];

	private static final Double error = 0.01;
	private static Double Iu, Il, Cc;

	public static void main(String[] args) {
		Iu = 1.0;
		Il = 0.0;
		double x;
		int N = Pyc.length, M = Pyc[0].length;

		for (int i = 0; i < Pyc.length; i++) {
			Qy[i] = (Pyc[i][0] * Pc[0] + Pyc[i][1] * Pc[1] + Pyc[i][2] * Pc[2] + Pyc[i][3] * Pc[3]);
		}

		while ((Iu - Il) > error) {
			for (int i = 0; i < M; i++) {
				double temp = 0.0;
				for (int j = 0; j < N; j++) {
					temp += Pyc[j][i] * Math.log(Pyc[j][i] / Qy[j]);
				}
				F[i] = Math.exp(temp);
			}
			x = (F[0] * Pc[0] + F[1] * Pc[1] + F[2] * Pc[2] + F[3] * Pc[3]);
			Il = Math.log10(x) / Math.log10(2.0);
			double max = 0.0;
			for (Double F1 : F) {
				if (F1 >= max) {
					max = F1;
				}
			}
			Iu = Math.log10(max) / Math.log10(2.0);
			if ((Iu - Il) < error) {
				Cc = Il;
				System.out.println("Cc : " + Cc);
				for (Double pc : Pc) {
					System.out.println("Pc : " + pc);
				}
				for (Double qy : Qy) {
					System.out.println("Qy : " + qy);
				}

				double temp2 = 0.0;
				for (int i = 0; i < M; i++) {
					double temp1 = 0.0;
					for (int j = 0; j < N; j++) {
						temp1 += Pyc[j][i] * Math.log(Pyc[j][i] / Qy[j]) / Math.log10(2.0);
					}
					temp2 += Pc[i] * temp1;
				}
				double I = temp2;
				System.out.println("I : " + I);
				break;
			} else {
				for (int i = 0; i < F.length; i++) {
					Pc[i] = (1 / x) * F[i] * Pc[i];
				}
				for (int i = 0; i < Pyc.length; i++) {
					Qy[i] = (Pyc[i][0] * Pc[0] + Pyc[i][1] * Pc[1] + Pyc[i][2] * Pc[2] + Pyc[i][3] * Pc[3]);
				}
				for (Double qy : Qy) {
					System.out.println("Qy : " + qy);
				}
			}
		}

	}
}
