package jmu.mapper;

import jmu.Util.DateTimeUtil;
import jmu.service.PlateRecognitionService;
import jmu.vo.DayRecord;
import jmu.vo.Record;
import jmu.vo.TempRecord;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class RecordMapperTest {

  @Autowired
  private RecordMapper recordMapper;
  @Autowired
  private PlateRecognitionService plateRecognitionService;

  @Test
  void queryRecord() {
    List<Record> list = recordMapper.queryRecord();
    System.out.println(list);
  }

  @Test
  void selectByCarnum() {
      String carnum = "鲁B907QU";
      List<Record> list = this.recordMapper.selectByCarnum(carnum);
      Record record = list.get(0);
        System.out.println(record);
      long time = (record.getROuttime().getTime()-record.getRIntime().getTime());
      long m = 30*60*1000;
      long count = 0;
      if (time%(30*60*1000) == 0){
          count = time / m;
      }
      else {
          count = time/m+1;
      }

      System.out.println(count);
  }

  @Test
  void selectTRByCarnum() {
//      String carnum = "闽D88888";
//      TempRecord tempRecord = this.recordMapper.selectTRByCarnum(carnum);
//      System.out.println(tempRecord);
  }

  @Test
  void insertRecord() {
//      String carnum = "闽D88888";
//      Record record = new Record();
//      TempRecord tempRecord = new TempRecord();
//      tempRecord = this.recordMapper.selectTRByCarnum(carnum);
//      record.setRCarnum(tempRecord.getTCarnum());
//      record.setRIntime(tempRecord.getTIntime());
//      record.setROuttime(new Date());
//      Integer num = this.recordMapper.insertRecord(record);
//      System.out.println(num);
  }

  @Test
  void insertTRecord() {
      String carnum = "闽D88888";
      Date inTime = new Date();
      System.out.println(inTime);
      TempRecord tempRecord = new TempRecord();
      tempRecord.setTCarnum(carnum);
      tempRecord.setTIntime(inTime);
      Integer num = this.recordMapper.insertTRecord(tempRecord);
      System.out.println(num);
  }

  @Test
  void deleteTRByCarnum() {
      String carnum = "鲁B907QU";
      Integer num = this.recordMapper.deleteTRByCarnum(carnum);
    System.out.println(num);
  }

  @Test
  void deleteByCarnum() {
      String carnum = "闽D23456";
      Integer num = this.recordMapper.deleteByCarnum(carnum);
      System.out.println(num);
  }

  @Test
  void deleteByRId(){
      Integer rId = 6;
      Integer num = this.recordMapper.deleteByRId(rId);
      System.out.println(num);
  }

  @Test
  void api(){
    System.out.println(this.plateRecognitionService.testAPI());
  }

  @Test
  void count(){
      List<DayRecord> dayRecords = this.recordMapper.selectRecordNumByDay();
    System.out.println(dayRecords);
  }

    @Test
    void searchByCarnum(){
        String carnum = "B";
        List<Record> recordList = this.recordMapper.searchByCarnum(carnum);
        System.out.println(recordList);
    }
}