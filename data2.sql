-- 회원 데이터
insert into memb(mno,email,pwd,name,tel) values(1,'user01@test.com',password('1111'),'학생1','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(2,'user02@test.com',password('1111'),'학생2','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(3,'user03@test.com',password('1111'),'학생3','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(4,'user04@test.com',password('1111'),'학생4','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(5,'user05@test.com',password('1111'),'학생5','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(6,'user06@test.com',password('1111'),'학생6','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(7,'user07@test.com',password('1111'),'학생7','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(8,'user08@test.com',password('1111'),'학생8','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(9,'user09@test.com',password('1111'),'학생9','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(10,'user10@test.com',password('1111'),'학생10','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(11,'user11@test.com',password('1111'),'학생11','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(12,'user12@test.com',password('1111'),'학생12','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(13,'user13@test.com',password('1111'),'학생13','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(14,'user14@test.com',password('1111'),'학생14','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(15,'user15@test.com',password('1111'),'학생15','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(16,'user16@test.com',password('1111'),'학생16','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(17,'user17@test.com',password('1111'),'학생17','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(18,'user18@test.com',password('1111'),'학생18','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(19,'user19@test.com',password('1111'),'학생19','1111-1111');
insert into memb(mno,email,pwd,name,tel) values(20,'user20@test.com',password('1111'),'학생20','1111-1111');

-- 학생 데이터
insert into stud(sno,work,lst_schl,schl_nm) values(1,'Y','학사','비트대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(2,'Y','학사','한국대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(3,'Y','학사','하버드대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(4,'N','고졸','비트고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(5,'N','고졸','성남고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(6,'Y','고졸','부산고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(7,'Y','학사',null);
insert into stud(sno,work,lst_schl,schl_nm) values(8,'N','학사',null);
insert into stud(sno,work,lst_schl,schl_nm) values(9,'Y',null,'비트고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(10,'N',null,'성남고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(11,'Y','학사','서울대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(12,'Y','학사','서울대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(13,'N','고졸','서초고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(14,'Y','박사','영남대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(15,'Y','박사','한양대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(16,'Y','박사','비트대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(17,'N','고졸','서초고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(18,'N','고졸','강남고등학교');
insert into stud(sno,work,lst_schl,schl_nm) values(19,'Y','학사','강남대학교');
insert into stud(sno,work,lst_schl,schl_nm) values(20,'Y','학사','영남대학교');

-- 콘텐츠 데이터
insert into content(cono,mno,rdt,vw_cnt) values(1,1,'2016-09-20 19:52:21',256);
insert into content(cono,mno,rdt,vw_cnt) values(2,5,'2016-04-25 11:05:05',458);
insert into content(cono,mno,rdt,vw_cnt) values(3,9,'2016-05-29 15:25:01',251);
insert into content(cono,mno,rdt,vw_cnt) values(4,13,'2016-03-27 19:45:41',1036);
insert into content(cono,mno,rdt,vw_cnt) values(5,17,'2015-12-23 08:52:31',835);

-- 프로젝트 데이터
insert into proj(pjno,titl,conts,sdt,edt) values(1,'HiBa','모임별로 일정을 관리해줄 뿐만아니라 정보제공과 앨범 만들기도 가능한 프로그램','2016-10-01','2017-01-01');
insert into proj(pjno,titl,conts,sdt,edt) values(2,'Home&Human','집주인과 세입자 사이의 원활한 소통을 도와주는 시스템','2016-05-01','2016-08-01');
insert into proj(pjno,titl,conts,sdt,edt) values(3,'WeTRIP','나홀로 여행족을을 위한 동행 매칭 시스템','2016-06-01','2016-09-01');
insert into proj(pjno,titl,conts,sdt,edt) values(4,'꿀팁팩토리','혼자보기 아까운 꿀팁들과 나만의 파일을 한 곳에 모아 저장 및 공유한다','2016-04-01','2016-08-01');
insert into proj(pjno,titl,conts,sdt,edt) values(5,'EasySafe','평소에 자주 접하는 식품들의 성분표, 읽을 수 없는 식품첨가물을 알기 쉽게 알려주는 앱과 웹','2016-01-01','2016-05-01');

-- 프로젝트 회원 데이터
insert into proj_memb(mno,pjno,rol) values(1,1,'팀장');
insert into proj_memb(mno,pjno,rol) values(2,1,'팀원');
insert into proj_memb(mno,pjno,rol) values(3,1,'팀원');
insert into proj_memb(mno,pjno,rol) values(4,1,'팀원');

insert into proj_memb(mno,pjno,rol) values(5,2,'팀장');
insert into proj_memb(mno,pjno,rol) values(6,2,'팀원');
insert into proj_memb(mno,pjno,rol) values(7,2,'팀원');
insert into proj_memb(mno,pjno,rol) values(8,2,'팀원');

insert into proj_memb(mno,pjno,rol) values(9,3,'팀장');
insert into proj_memb(mno,pjno,rol) values(10,3,'팀원');
insert into proj_memb(mno,pjno,rol) values(11,3,'팀원');
insert into proj_memb(mno,pjno,rol) values(12,3,'팀원');

insert into proj_memb(mno,pjno,rol) values(13,4,'팀장');
insert into proj_memb(mno,pjno,rol) values(14,4,'팀원');
insert into proj_memb(mno,pjno,rol) values(15,4,'팀원');
insert into proj_memb(mno,pjno,rol) values(16,4,'팀원');

insert into proj_memb(mno,pjno,rol) values(17,5,'팀장');
insert into proj_memb(mno,pjno,rol) values(18,5,'팀원');
insert into proj_memb(mno,pjno,rol) values(19,5,'팀원');
insert into proj_memb(mno,pjno,rol) values(20,5,'팀원');





