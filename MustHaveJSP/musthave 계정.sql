-- 테이블 목록 조회

select * from tab;

drop table member;

drop table board;

drop SEQUENCE seq_board_num;

-- 회원 테이블 만들기 
create table member(
    id varchar2(10) not null,
    pass varchar2(10) not null,
    name varchar2(30) not null,
    regidate date default sysdate not null,
    primary key(id)
);

-- 모델1 방식의 게시판 테이블 만들기

create table board (
    num number primary key,
    title varchar2(200) not null,
    content varchar2(2000) not null,
    id varchar2(10) not null,
    postdate date default sysdate not null,
    visitcount number(6)
);

-- 외래키 설정

alter table board
    add constraint board_mem_fk foreign key (id)
    references member (id);
-- 시퀀스 생성

create sequence seq_board_num
    increment by 1 -- 1씩증가
    start with 1  -- 시작값 1
    minvalue 1  -- 최소값1
    nomaxvalue  -- 최대값은 무한대
    nocycle -- 순환하지 않음
    nocache;    -- 캐시 안 함.
    
-- 더미 데이터 입력
insert into member (id, pass, name) values ('musthave', '1234', '머스트해브');
insert into board (num, title, content, id, postdate, visitcount)
    values (seq_board_num.nextval, '제목1입니다.', '내용1입니다.', 'musthave',
    sysdate, 0);

commit;

SELECT COUNT(*) FROM board WHERE title like '%2%';
SELECT * FROM board WHERE title like '%1%';

INSERT INTO board VALUES (seq_board_num.nextval, '지금은 봄입니다', '봄의왈츠', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '지금은 여름입니다', '여름향기', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '지금은 가을입니다', '가을동화', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '지금은 겨울입니다', '겨울연가', 'musthave', sysdate, 0);

commit;

