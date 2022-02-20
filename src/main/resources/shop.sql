create table t_customer (cusNo int ,
                cusName varchar(20),
                 telePhone int.
                 address varchar2,
                 password varchar(12),
                 primary key (cusNo )
)ENGINE=InnoDB DEFAULT CHARSET = utf8;

create table t_order(
               orderNo int,
               goodsId int,
               goodsCount int,
               goodsSumMoney double(16,2),
               cusNo int,
               OrderStatus varchar(1),	
	primary key(orderNo)
)ENGINE=InnoDB DEFAULT CHARSET = utf8;

create table t_goods(
               goodsNo int,
               goodsName varchar2,
               goodsUnit varchar2,
               goodsPrice double(16,2),
	primary key (goodsNo)
 )ENGINE=InnoDB DEFAULT CHARSET = utf8;

create table t_oder_goods （
	orderNo  int,
	goodsNo int,
	primary key(orderNo ,goodsNo)
）ENGINE=InnoDB DEFAULT CHARSET = utf8;


                                      
                                      
                                       