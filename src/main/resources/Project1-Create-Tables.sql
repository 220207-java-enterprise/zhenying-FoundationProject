drop table if exists ers_reimbursements;
drop table if exists ers_reimbursement_types;
drop table if exists ers_reimbursement_statuses;
drop table if exists ers_users;
drop table if exists ers_user_roles;

create table ers_user_roles(
	role_id		varchar primary key,
	role		varchar unique
);

create table ers_users(
	user_id		varchar primary key,
	username	varchar unique not null,
	email		varchar unique not null,
	password	varchar not null,
	given_name	varchar not null,
	surname		varchar not null,
	is_active	boolean,
	role_id		varchar not null,
	
	constraint role_id_fk
	foreign key (role_id)
	references ers_user_roles (role_id)
);

create table ers_reimbursement_statuses(
	status_id	varchar primary key,
	status		varchar unique 
);

create table ers_reimbursement_types(
	type_id		varchar primary key,
	type		varchar unique 
);

create table ers_reimbursements(
	reimb_id	varchar primary key,
	amount 		numeric (6,2) not null,
	submitted	timestamp not null,
	resolved	timestamp,
	description	varchar not null,
	receipt		bytea ,
	payment_id	varchar,
	author_id	varchar not null,
	resolver_id	varchar,
	status_id	varchar not null,
	type_id 	varchar not null,
	
	constraint author_id_fk 
	foreign key (author_id)
	references ers_users(user_id),
	
	constraint resolver_id_fk
	foreign key (resolver_id)
	references ers_users(user_id),
	
	constraint status_id_fk
	foreign key (status_id)
	references ers_reimbursement_statuses(status_id),
	
	constraint type_id_fk
	foreign key (type_id)
	references ers_reimbursement_types(type_id)
);





