import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDay } from '@fortawesome/free-solid-svg-icons'

export const ScheduleWeekMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/schedule/week" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faCalendarDay} />
      <span>
        <Translate contentKey="global.menu.scheduleWeek">Week</Translate>
      </span>
    </NavLink>
  </NavItem>
);