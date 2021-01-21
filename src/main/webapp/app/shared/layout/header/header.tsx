import './header.scss';

import React, { useState } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import { ScheduleWeekMenu } from 'app/modules/schedule/schedule-week-components';
import { ScheduleSemesterMenu } from 'app/modules/schedule/schedule-semester-components';
import { AsksForEnrollmentOverLimitMenu } from 'app/modules/asks-for-enrollment-over-limit/asks-for-enrollment-over-limit-components';
import { SemestersMenu } from 'app/modules/semesters/semesters-components';
import { EnrollmentsMenu } from 'app/modules/enrollments/enrollments-components';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isStudent: boolean;
  isLecturer: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    props.onLocaleChange(langKey);
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar" />
      <Navbar dark expand="sm" fixed="top" className="bg-dark">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <Brand />
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ml-auto" navbar>
            <Home />
            {props.isAuthenticated && (props.isStudent || props.isLecturer) && <ScheduleWeekMenu />}
            {props.isAuthenticated && (props.isStudent || props.isLecturer) && <ScheduleSemesterMenu />}
            {props.isAuthenticated && (props.isStudent || props.isLecturer) && <AsksForEnrollmentOverLimitMenu />}
            {props.isAuthenticated && props.isStudent && <SemestersMenu />}
            {props.isAuthenticated && props.isStudent && <EnrollmentsMenu />}
            {props.isAuthenticated && props.isAdmin && <EntitiesMenu />}
            {props.isAuthenticated && props.isAdmin && (
              <AdminMenu showSwagger={props.isSwaggerEnabled} showDatabase={!props.isInProduction} />
            )}
            <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
