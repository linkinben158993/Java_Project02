package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dao.LopDao;
import dao.MonDao;
import dao.SinhVienDao;
import entity.DSSV_MON;
import entity.Lop;
import entity.Mon;
import entity.SinhVien;

public class FileParser {

	public FileParser() {

	}

	public List<SinhVien> readFromCSV_SinhVien(String fileName) {
		List<String[]> parsedStuff = new ArrayList<String[]>();
		List<SinhVien> sinhVien = new ArrayList<SinhVien>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"))) {
			String line = br.readLine();
			while (line != null) {
				String[] stuff = line.split(",");
				parsedStuff.add(stuff);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Lop lop = new Lop();

		for (int i = 0; i < parsedStuff.size(); ++i) {
			if (i == 0) {
				lop.set_maLop(parsedStuff.get(i)[0]);
			} else if (i == 1) {
				continue;
			} else {

				// Do file input có đầu vào là lớp nên phải set lớp nếu như lớp chưa tồn tại. Do
				// dùng hàm saveorupdate ở insert nên nếu lớp đã tồn tại thì sẽ giữ nguyên
				LopDao lopDao = new LopDao();
				List<Lop> lops = lopDao.findAll();
				Lop foundLop = new Lop().findByML(lops, lop);

				if (foundLop == null) {
					System.out.println("Thêm mới lớp!");
					lopDao.insert(lop);

					SinhVien newsinhVien = new SinhVien();
					newsinhVien.set_mssv(parsedStuff.get(i)[1]);
					newsinhVien.set_ten(parsedStuff.get(i)[2]);
					newsinhVien.set_gioiTinh(parsedStuff.get(i)[3]);
					newsinhVien.set_cmnd(parsedStuff.get(i)[4]);

					newsinhVien.setMa_quyen(2);
					newsinhVien.setMa_lop(lop.get_maLop().toString());

					newsinhVien.set_password(BCrypt.hashpw(newsinhVien.get_cmnd(), BCrypt.gensalt(12)));

					sinhVien.add(newsinhVien);

					// Thêm sinh viên
					SinhVienDao sinhVienDao = new SinhVienDao();
					sinhVienDao.insert(newsinhVien);
				} else {
					System.out.println("Lớp đã tồn tại thêm mới sinh viên!");

					SinhVien newsinhVien = new SinhVien();
					newsinhVien.set_mssv(parsedStuff.get(i)[1]);
					newsinhVien.set_ten(parsedStuff.get(i)[2]);
					newsinhVien.set_gioiTinh(parsedStuff.get(i)[3]);
					newsinhVien.set_cmnd(parsedStuff.get(i)[4]);

					newsinhVien.setMa_quyen(2);
					newsinhVien.setMa_lop(lop.get_maLop().toString());

					newsinhVien.set_password(BCrypt.hashpw(newsinhVien.get_cmnd(), BCrypt.gensalt(12)));

					sinhVien.add(newsinhVien);

					// Thêm sinh viên
					SinhVienDao sinhVienDao = new SinhVienDao();
					sinhVienDao.insert(newsinhVien);
				}
			}
		}

		return sinhVien;
	}

	public List<Mon> readFromCSV_Mon(String fileName) {
		List<String[]> parsedStuff = new ArrayList<String[]>();
		List<Mon> mons = new ArrayList<Mon>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"))) {
			String line = br.readLine();
			while (line != null) {
				String[] stuff = line.split(",");
				parsedStuff.add(stuff);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < parsedStuff.size(); ++i) {
			Lop lop = new Lop();
			
			if (i == 0) {
				lop.set_maLop(parsedStuff.get(i)[0]);


			}
			else if(i == 1) {
				continue;
			}
			else {
				/*
				 * Mon mon = new Mon(); mon.set_maMon(parsedStuff.get(i)[0]);
				 * mon.set_tenMon(parsedStuff.get(i)[1]); mon.set_maMon(parsedStuff.get(i)[2]);
				 * 
				 * MonDao monDao = new MonDao(); monDao.insert(mon);
				 * 
				 * DSSV_MON dssv_MON = new DSSV_MON(); dssv_MON.set_malopMon(lop.get_maLop());
				 * dssv_MON.set_mssv(_mssv);
				 */
			}
		}

		return mons;
	}

	public List<Lop> readFromCSV_Lop() {
		List<Lop> lop = new ArrayList<Lop>();

		return lop;
	}
}
