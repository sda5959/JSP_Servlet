-- ���̺� ��� ��ȸ

select * from tab;

drop table member;

drop table board;

drop SEQUENCE seq_board_num;

-- ȸ�� ���̺� ����� 
create table member(
    id varchar2(10) not null,
    pass varchar2(10) not null,
    name varchar2(30) not null,
    regidate date default sysdate not null,
    primary key(id)
);

-- ��1 ����� �Խ��� ���̺� �����

create table board (
    num number primary key,
    title varchar2(200) not null,
    content varchar2(2000) not null,
    id varchar2(10) not null,
    postdate date default sysdate not null,
    visitcount number(6)
);

-- �ܷ�Ű ����

alter table board
    add constraint board_mem_fk foreign key (id)
    references member (id);
-- ������ ����

create sequence seq_board_num
    increment by 1 -- 1������
    start with 1  -- ���۰� 1
    minvalue 1  -- �ּҰ�1
    nomaxvalue  -- �ִ밪�� ���Ѵ�
    nocycle -- ��ȯ���� ����
    nocache;    -- ĳ�� �� ��.
    
-- ���� ������ �Է�
insert into member (id, pass, name) values ('musthave', '1234', '�ӽ�Ʈ�غ�');
insert into board (num, title, content, id, postdate, visitcount)
    values (seq_board_num.nextval, '����1�Դϴ�.', '����1�Դϴ�.', 'musthave',
    sysdate, 0);

commit;

SELECT COUNT(*) FROM board WHERE title like '%2%';
SELECT * FROM board WHERE title like '%1%';

INSERT INTO board VALUES (seq_board_num.nextval, '������ ���Դϴ�', '���ǿ���', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '������ �����Դϴ�', '�������', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '������ �����Դϴ�', '������ȭ', 'musthave', sysdate, 0);
INSERT INTO board VALUES (seq_board_num.nextval, '������ �ܿ��Դϴ�', '�ܿ￬��', 'musthave', sysdate, 0);

commit;

