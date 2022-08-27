package com.dayalbagh.snc.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;



@Entity
@NamedQueries({

@NamedQuery (name="getParentMenuItems",
	//query="SELECT parent.id as id, parent.name as name, parent.lft as lft , "
	//		+ " parent.rgt as rgt , parent.route  as route FROM Menu AS node, Menu AS parent" +
query="SELECT  parent "
		+ "  FROM Menu AS node, Menu AS parent" + 
		
        " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
        " AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"+
        " and parent.id > 1 and parent.lft+1 <>  parent.rgt"+
        " group by parent.id order by parent.lft"
		),
		
@NamedQuery (name="getChildrenMenuItems",
	query ="Select  node "+
		" FROM Menu AS node,Menu AS parent"+
	    " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
		" and node.rgt <parent.rgt"+
		" AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"+
	    " AND parent.id = :MenuId "+
		" ORDER BY node.lft"
	),
@NamedQuery (name="getdefaultrole",
query =" select u from UserRoles u where user_id=:userid and default_role=:defaultrole"
),


})

//@NamedQuery (name="getStudentDetail",
//query =
//"          select new  edu.dei.examination.cmsexm.domain.Verification(sp.enrollment_number,sp.roll_number,sp.cgpa,sp.passed_from_session,"+
// "  sm.student_first_name,stt.component_description,pm.program_name) "+
//
//"    from StudentProgram as  sp " + 
//"                 join StudentMaster as sm on sp.enrollment_number=sm.enrollment_number " + 
//"                 join ProgramMaster pm on pm.program_id = sp.program_id " + 
//
//"                 join SystemTableTwo  as stt on stt.component_code=sp.division " + 
//"                 where sp.enrollment_number= :enrolmentno and sp.program_status = 'PAS' " + 
//"                 and stt.group_code= 'DVSCOD' "
//),
//}
//)





//@SqlResultSetMapping(name = "StudentVerificationmap" ,classes =
//{@ConstructorResult(targetClass = Verification.class,
//
//columns = {@ColumnResult(name="enrollment_number")}) })










//@SqlResultSetMapping(name="StudentVerificationmap2" ,entities = {@EntityResult(entityClass = Verification.class)} )

public class Querybase {
	 @Id
     private long id;
}
