package jmu.service.impl;

import jmu.mapper.NoticeMapper;
import jmu.service.NoticeService;
import jmu.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> queryNotice() {
        return this.noticeMapper.queryNotice();
    }

    @Override
    public Notice selectByNId(Integer nId){
        return this.noticeMapper.selectByNId(nId);
    }

    @Override
    public Integer insertNotice(Notice notice) {
        return this.noticeMapper.insertNotice(notice);
    }

    @Override
    public Integer deleteByNId(Integer nId) {
        return this.noticeMapper.deleteByNId(nId);
    }

    @Override
    public Integer updateNotice(Notice notice) {
        return this.noticeMapper.updateNotice(notice);
    }
}
