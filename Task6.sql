select res.name as Company,
       sum(exp.value) as Money
from
     listexpenses.expenses as exp,
     listexpenses.receivers as res
where
    exp.receiver=res.num
group by res.name;



select paydate as Date,
       sum(value) as Sum
from
     expenses
where
    paydate=(select paydate from expenses where value=(select max(value) from expenses));



select paydate as Date,
       max(value) as Payment
from
     expenses
where
    paydate=(select paydate from
        (select max(val),money.paydate from
                  (select sum(value) as val,paydate from expenses group by paydate) as money
        )as bestDay
            );

