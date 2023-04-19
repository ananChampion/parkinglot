package jmu.mapper;

import jmu.vo.Notice;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class NoticeMapperTest {

    @Autowired
    private NoticeMapper noticeMapper;

    @Test
    void queryNotice() {
        List<Notice> notices = this.noticeMapper.queryNotice();
    System.out.println(notices);
    }

    @Test
    void insertNotice() {
        Notice notice = new Notice();
        notice.setNText("测试一下");
        notice.setNTitle("测试");
        notice.setNTime(new Date());
        this.noticeMapper.insertNotice(notice);
    }

    @Test
    void deleteByNId() {
        Integer nId = 2;
        this.noticeMapper.deleteByNId(nId);
    }

    @Test
    void updateNotice() {
        Notice notice = new Notice();
        notice.setNText("测试一下");
        notice.setNTitle("修改");
        notice.setNTime(new Date());
        notice.setNId(3);
        this.noticeMapper.updateNotice(notice);
    }
}