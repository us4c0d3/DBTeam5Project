--Q1 - type1
-- payment 중에서 payment_type이 'Cash'인 payment의 order_id와 customer_id를 출력
SELECT *
FROM payment p
WHERE p.payment_type = 'Cash';


--Q2 - type2
-- payment 중에서 payment_type이 'Cash'인 customer의 이름과 폰번호를 중복없이 출력
SELECT distinct cu.name, cu.phone_number
FROM customer cu, payment p
WHERE cu.customer_id = p.customer_id
AND p.payment_type = 'Cash';


--Q3 - type3
-- menu_item 중에서 ingredient를 5개 이하로 사용한 menu_item의 이름과 그 menu_item의 ingredient 개수을 출력
SELECT mi.name, COUNT(*)
FROM menu_item mi, ingredient i, need n
WHERE n.item_id = mi.item_id
AND n.ingredient_id = i.ingredient_id
GROUP BY mi.name, mi.item_id
HAVING COUNT(*) <= 5;


--Q4 - type4
-- 현재 soldout된 menu_item을 주문했던 customer의 customer_id와 이름을 출력
SELECT cu.customer_id, cu.name
FROM customer cu
WHERE cu.customer_id IN (
    SELECT po.customer_id
    FROM menu_item mi, part_of po
    WHERE mi.item_id = po.item_id
    AND mi.soldout = 'T');


--Q5 - type5
-- chef 중에서 payment를 하나도 처리하지 않은 chef의 이름과 phone_number를 출력
SELECT ch.name, ch.phone_number
FROM chef ch
WHERE NOT EXISTS (
    SELECT *
    FROM payment p
    WHERE ch.chef_id = p.chef_id);


--Q6 - type6
-- menu_item 중에서 2023.01.01 ~ 2023.12.31까지 게시된 menu에 포함된 menu_item의 이름을 출력
SELECT mi.name
FROM menu_item mi
WHERE (mi.item_id) IN (
    SELECT co.item_id
    FROM contains co, menu me
    WHERE co.menu_id = me.menu_id
    AND me.start_date >= TO_DATE('20230101')
    AND me.end_date <= TO_DATE('20231231'));


--Q7 - type7
-- 각 menu_item의 이름과 menu_item의 수량, menu_item에 필요한 재료의 총 수량을 출력
SELECT mi.name, mi.item_quantity, iv.total_ingredient_quantity
FROM menu_item mi
JOIN (
    SELECT n.item_id, SUM(i.quantity) AS total_ingredient_quantity
    FROM need n, ingredient i
    WHERE n.ingredient_id = i.ingredient_id
    GROUP BY n.item_id
) iv ON mi.item_id = iv.item_id;


--Q8 - type8
-- 어떤 menu에도 속하지 않는 menu_item의 이름과 그 menu_item의 ingredient 개수를 menu_item의 이름의 오름차순으로 출력
SELECT mi.name, COUNT(*)
FROM menu_item mi, ingredient i, need n
WHERE n.item_id = mi.item_id
AND n.ingredient_id = i.ingredient_id
AND EXISTS (
    SELECT *
    FROM menu me, contains co
    WHERE me.menu_id = co.menu_id)
GROUP BY mi.name, mi.item_id
ORDER BY mi.name;


--Q9 - type9
-- 각 menu_item에 대해 그것을 관리하는 manager의 이름과 그 menu_item이 몇 번 주문되었는지의 총 수량을 출력
SELECT 
    m.name AS manager_name,
    mi.name AS menu_item_name,
    SUM(po.amount) AS total_ordered
FROM 
    menu_item mi
JOIN 
    managed_item mani ON mi.item_id = mani.item_id
JOIN 
    manager m ON mani.manager_id = m.manager_id
LEFT JOIN 
    part_of po ON mi.item_id = po.item_id
GROUP BY 
    m.name, mi.name
ORDER BY 
    m.name, total_ordered DESC;



--Q10 - type10
-- menu를 관리하면서 menu_item을 관리하는 manager의 이름을 출력
SELECT ma.name
FROM manager ma
WHERE ma.manager_id IN ((
    SELECT em.manager_id
    FROM menu me, edited_menu em
    WHERE me.menu_id = em.menu_id)
    INTERSECT (
    SELECT mg.manager_id
    FROM menu_item mi, managed_item mg
    WHERE mi.item_id = mg.item_id));


--Q11 - type1
-- menu_item 중에서 soldout이 'T'인 item을 출력해라.
SELECT *
FROM menu_item mi
WHERE mi.soldout = 'T';


--Q12 - type9
-- customer_id가 'CU000001'인 사람이 지금까지 주문한 메뉴 항목의 총 가격을 계산하고, 그 결과를 order별로 그룹화하여 출력
SELECT p.order_id, SUM(mi.unit_price * po.amount) AS total_order_price
FROM customer c
JOIN PAYMENT p ON c.customer_id = p.customer_id
JOIN part_of po ON p.order_id = po.order_id AND p.customer_id = po.customer_id
JOIN menu_item mi ON po.item_id = mi.item_id
WHERE c.customer_id = 'CU000001'
GROUP BY p.order_id
ORDER BY p.order_id;


--Q13 - type4
-- menu_item의 category가 'Breakfast Menu'인 menu_item의 unit_price 평균보다 total_price가 더 큰 payment의 order_id, customer_id, total_price를 출력
SELECT p.order_id, p.customer_id, p.total_price
FROM PAYMENT p
WHERE p.total_price > (
    SELECT AVG(mi.unit_price)
    FROM menu_item mi
    WHERE mi.category = 'Breakfast Menu'
);



--Q14 - type5
-- 모든 menu_item 중에서 적어도 한 번 이상 주문된 항목의 이름을 출력
SELECT name
FROM menu_item mi
WHERE EXISTS (
    SELECT *
    FROM part_of p
    WHERE mi.item_id = p.item_id
);


--Q15 - type6
-- ingredient_id가 need테이블에 등록된 ingredient의 name과 unit_price를 출력
SELECT name, unit_price
FROM ingredient
WHERE ingredient_id IN (
    SELECT ingredient_id
    FROM need
);


--Q16 - type10
-- 모든 customer가 본 menu와 manager가 편집한 menu를 조회하여 그 중 겹치는 menu의 menu_id를 출력
(SELECT menu_id FROM views)
INTERSECT
(SELECT menu_id FROM edited_menu);


--Q17 - type7
-- 각 menu_item별로 몇 번 주문되었는지 확인하고, 가장 많이 주문된 상위 5개의 menu_item의 이름과 주문 횟수를 출력
SELECT inlview.item_name, inlview.order_count
FROM (
    SELECT mi.name AS item_name, COUNT(p.item_id) AS order_count
    FROM menu_item mi
    LEFT JOIN part_of p ON mi.item_id = p.item_id
    GROUP BY mi.name
) inlview
ORDER BY inlview.order_count DESC
FETCH FIRST 5 ROWS ONLY;


--Q18 - type2
-- payment에서 customer의 이름, 주문한 menu_item의 이름 그리고 해당 menu_item을 관리하는 manager의 이름을 출력
SELECT 
    c.name AS customer_name,
    mi.name AS menu_item_name,
    m.name AS manager_name
FROM 
    customer c, 
    payment p, 
    part_of po, 
    menu_item mi, 
    managed_item mani, 
    manager m
WHERE 
    c.customer_id = p.customer_id
    AND p.order_id = po.order_id
    AND po.item_id = mi.item_id
    AND mi.item_id = mani.item_id
    AND mani.manager_id = m.manager_id;



--Q19 - type6
-- 같은 category의 menu_item을 3개 이상 관리하는 manager의 이름과 phone_number를 이름의 오름차순으로 출력
SELECT ma.name, ma.phone_number
FROM manager ma
WHERE ma.manager_id IN (
    SELECT mg.manager_id
    FROM managed_item mg, menu_item mi
    WHERE mi.item_id = mg.item_id
    GROUP BY mg.manager_id, mi.category
    HAVING COUNT(*) >= 3)
ORDER BY ma.name;


--Q20 - type8
-- customer가 주문한 menu_item의 이름과 해당 menu_item을 만드는 chef의 이름을 출력. 결과는 chef의 이름 순으로 정렬
SELECT 
    c.name AS customer_name,
    mi.name AS menu_item_name,
    ch.name AS chef_name
FROM 
    customer c, 
    payment p, 
    part_of po, 
    menu_item mi, 
    chef ch
WHERE 
    c.customer_id = p.customer_id
    AND p.order_id = po.order_id
    AND po.item_id = mi.item_id
    AND p.chef_id = ch.chef_id
ORDER BY 
    ch.name;