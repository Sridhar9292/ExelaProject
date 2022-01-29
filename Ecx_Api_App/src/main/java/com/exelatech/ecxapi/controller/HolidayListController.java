package com.exelatech.ecxapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.HolidayListMapper;
import com.exelatech.ecxapi.model.HolidayList;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/holiday")
public class HolidayListController {
	@Autowired
	private HolidayListMapper holidayListMapper;


	@PreAuthorize("hasAnyAuthority('_administration:holiday:manage','_administration:holiday:view')")
	@GetMapping
	public List<HolidayList> holidayList() throws NotFoundException {	
		List<HolidayList> holidayList=null;
		try {
			holidayList=holidayListMapper.getAll();
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return holidayList;
	}
	@PreAuthorize("hasAnyAuthority('_administration:holiday:manage','_administration:holiday:view')")
	@Operation(summary= "Get Holiday date details - input date format DDMMYYYY")
	@GetMapping("/{date}")
	public HolidayList getHolidayNames(@PathVariable String date) throws NotFoundException {
		HolidayList  holidayList=null; 
			try {
			SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
			Date date_check= (Date) sourceFormat.parse(date);
			holidayList=holidayListMapper.get(date);
			} catch (final Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				throw new NotFoundException(e.getMessage());
			}				
		return holidayList;
	}
	@PreAuthorize("hasAnyAuthority('_administration:holiday:manage','_administration:holiday:edit')")
	@Operation(summary= " Update Holiday date - input date format DDMMYYYY")
	@PutMapping("/{old_date}")
	public ResponseEntity<String> updateHoliday(@RequestBody HolidayList holidayList,@PathVariable("old_date") String old_date) throws NotFoundException {
		int count=0;
		try {		
			SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
			Date newDate = null, oldDate = null;
			int saturdayFlag = 0;
			int sundayFlag = 0;
			HolidayList holiday = null;
			newDate = (Date) sourceFormat.parse(holidayList.getHolidayDate());
			oldDate = (Date) sourceFormat.parse(old_date);
			System.out.println("newDate  ====>>>"+newDate);
			System.out.println("oldDate  ====>>>"+oldDate);
			holiday = holidayListMapper.updateCheckExist(holidayList.getHolidayId(),old_date);
			
			System.out.println("checkExist  ====>>>"+holiday);
			if (holidayList.getSundayFlag() != null) {
				sundayFlag = 1;
			}
			if (holidayList.getSaturdayFlag() != null) {
				saturdayFlag = 1;
			}
			

			if (holiday != null) {
				System.out.println("sourceDate update ====>>>"+holidayList.getHolidayDateFormat());
				count=holidayListMapper.updateHoliday(
						newDate, 
						oldDate, 
						saturdayFlag,
						sundayFlag,
						holidayList.getHolidayId());		

			}
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				return ResponseEntity.ok().body(holidayList.getHolidayId() +"--"+ Constants.NOT_EXIST); 
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}




	@PreAuthorize("hasAnyAuthority('_administration:holiday:manage','_administration:holiday:add')")
    @Operation(summary= "Insert Holiday - input date format DDMMYYYY")
	@PostMapping
	public ResponseEntity<String> insertHoliday(@RequestBody HolidayList holidayList) throws NotFoundException {
		HolidayList holiday = null;
		int count=0;
		try {
			//System.out.println("Holiday List Check===>>"+holidayList.getHolidayName());
			//System.out.println("Holiday List Check getHoliId===>>"+holidayList.getHolidayId());
			if (holidayList != null) {			 
			
				System.out.println("holidayList.getHolidayDate()  ====>>>"+holidayList.getHolidayDate());
				SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
				Date sourceDate = (Date) sourceFormat.parse(holidayList.getHolidayDate());
			
				holiday = holidayListMapper.checkExist(holidayList.getHolidayId(),holidayList.getHolidayDate());

				System.out.println("checkExist  ====>>>"+holiday);

				holidayList.setHolidayDateFormat(sourceDate);

				if (holidayList.getSundayFlag() != null) {
					holidayList.setSundayFlag(1);
				} else {
					holidayList.setSundayFlag(0);
				}

				if (holidayList.getSaturdayFlag() != null) {
					holidayList.setSaturdayFlag(1);
				} else {
					holidayList.setSaturdayFlag(0);
				}

			}
			if (holiday == null) {
				System.out.println("sourceDate  ====>>>"+holidayList.getHolidayDateFormat());
				count=holidayListMapper.insertHoliday(holidayList);
			}
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("holidaydate and Id "+Constants.ALREDY_EXIST);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("holidaydate and Id "+Constants.ALREDY_EXIST);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:holiday:manage','_administration:holiday:delete')")
	@DeleteMapping("/{holidayId}/{date}")
	public ResponseEntity<String> deleteHoliday(@PathVariable("holidayId") String holidayId, 
			@PathVariable("date") String date) throws NotFoundException {
		int count=0;
		try {
			count=holidayListMapper.deleteHoliday(new SimpleDateFormat("ddMMyyyy")
					.parse(date),Integer.parseInt(holidayId));
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("holidaydate and Id "+Constants.NOT_EXIST); 
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}


	/*
	@GetMapping
	public List<HolidayList> getAll() {

		List<HolidayList> holidayList = new LinkedList();
		HolidayList holiday = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
		for (HolidayList holidayIter : holidayListMapper.getAll()) {

			String holidaySplitter[] = holidayIter.getHolidayDate().split(",");
			String saturdaySplitter[] = holidayIter.getSaturdayHolder().split(
					",");
			String sundaySplitter[] = holidayIter.getSundayHolder().split(",");

			boolean saturday[] = new boolean[4];
			boolean sunday[] = new boolean[4];

			holiday = new HolidayList();

			for (int i = 0; i < saturdaySplitter.length; i++) {
				if (saturdaySplitter[i].equals("1")) {
					saturday[i] = true;
				}
				if (sundaySplitter[i].equals("1")) {
					sunday[i] = true;
				}
			}

			holiday.setHolidayName(holidayIter.getHolidayName());
			holiday.setHolidayIdList(holidayIter.getHolidayIdList());

			if (holidaySplitter.length > 0) {
				List<Integer> list = new LinkedList();
				int currentYear = Integer.parseInt(new SimpleDateFormat("yy").format(new Date(0)));
				for (String h : holidaySplitter) {
					try {
						list.add(Integer.parseInt(new SimpleDateFormat("yy")
								.format(format.parse(h))));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				int j = 0;
				for (int i = 0; i < 4; i++) {
					String forHoliday = null;
					boolean forSaturday = false;
					boolean forSunday = false;
					boolean found = false;
					if (list.contains(currentYear++)) {
						forHoliday = holidaySplitter[j];
						forSaturday = saturday[j];
						forSunday = sunday[j];
						found = true;
						j++;
					} else {
						forHoliday = "";
					}

					if (i == 0) {
						if (found) {
							if (forSaturday) {
								holiday.setHolidayDate1(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SATURDAY));
							} else if (forSunday) {
								holiday.setHolidayDate1(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SUNDAY));
							} else {
								holiday.setHolidayDate1(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.WEEKDAY));
							}
						} else {
							holiday.setHolidayDate1("");
						}

					} else if (i == 1) {
						if (found) {
							if (forSaturday) {
								holiday.setHolidayDate2(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SATURDAY));
							} else if (forSunday) {
								holiday.setHolidayDate2(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SUNDAY));
							} else {
								holiday.setHolidayDate2(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.WEEKDAY));
							}
						} else {
							holiday.setHolidayDate2("");
						}

					} else if (i == 2) {
						if (found) {
							if (forSaturday) {
								holiday.setHolidayDate3(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SATURDAY));
							} else if (forSunday) {
								holiday.setHolidayDate3(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SUNDAY));
							} else {
								holiday.setHolidayDate3(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.WEEKDAY));
							}
						} else {
							holiday.setHolidayDate3("");
						}
					} else if (i == 3) {
						if (found) {
							if (forSaturday) {
								holiday.setHolidayDate4(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SATURDAY));
							} else if (forSunday) {
								holiday.setHolidayDate4(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.SUNDAY));
							} else {
								holiday.setHolidayDate4(checkingWeekendDaysNew(
										forHoliday, holidayListMapper.WEEKDAY));
							}
						} else {
							holiday.setHolidayDate4("");
						}
					}

				}

			}

			else {

				holiday.setHolidayDate1("");
				holiday.setHolidayDate2("");
				holiday.setHolidayDate3("");
				holiday.setHolidayDate4("");

			}

			holidayList.add(holiday);
		}

		return holidayList;
	}
	 */
	private String checkingWeekendDaysNew(String date, String day) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
		String result = null;
		try {
			if (day.equalsIgnoreCase(holidayListMapper.SATURDAY)) {
				result = new SimpleDateFormat("MMMM dd").format(	
						format.parse(date)).concat("(1)");
			} else if (day.equalsIgnoreCase(holidayListMapper.SUNDAY)) {
				result = new SimpleDateFormat("MMMM dd").format(
						format.parse(date)).concat("(2)");
			} else {
				result = new SimpleDateFormat("MMMM dd").format(format
						.parse(date));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	@PutMapping("/{holidayId}/{date}")
	public ResponseEntity<String> updateHoliday(@RequestBody HolidayList holidayList,@PathVariable("holidayId") String holidayId,
			@PathVariable("date") String date) {

		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date newDate = null, oldDate = null;
		int saturdayFlag = 0;
		int sundayFlag = 0;
		HolidayList holiday = null;
		try {
			newDate = (Date) sourceFormat.parse(holidayList.getHolidayDate());
			holiday = holidayListMapper.updateCheckExist(new SimpleDateFormat(
					"MM-dd").format(newDate), new SimpleDateFormat("yyyy")
					.format(newDate));
			if (holidayList.getSundayFlag() != null) {
				sundayFlag = 1;
			}
			if (holidayList.getSaturdayFlag() != null) {
				saturdayFlag = 1;
			}
			oldDate = (Date) sourceFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (holiday == null) {
			holidayListMapper.updateHoliday(newDate, oldDate, saturdayFlag,
					//arul  start 
					sundayFlag,Integer.parseInt(holidayId));					
					//sundayFlag,holidayList.getHolidayId().getHoliId());
			      //arul ends 
			return ResponseEntity.ok().body("success");
		}
		return ResponseEntity.ok().body("Not Updated");
	}

	 */
	public String[] getStaticHolidayNames() {

		String[] holidayNames = { "New Year's Day,1",
				"Martin Luther King Jr.'s Birthday,2",
				"Washington's Birthday,3", "Memorial Day,4",
				"Independence Day,5", "Labor Day,6", "Columbus Day,7",
				"Veterans Day,8", "Thanksgiving Day,9", "Christmas Day,10" };

		return holidayNames;

	}


}
