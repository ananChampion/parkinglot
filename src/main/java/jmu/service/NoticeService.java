package jmu.service;

import jmu.vo.Notice;

import java.util.List;

public interface NoticeService {
    public List<Notice> queryNotice();
    public Notice selectByNId(Integer nId);
    public Integer insertNotice(Notice notice);
    public Integer deleteByNId(Integer nId);
    public Integer updateNotice(Notice notice);
    public Integer insertNoticeForUser(Notice notice);
    public List<Notice> selectByNUId(Integer nUId);
}
