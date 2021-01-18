import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDay } from '@fortawesome/free-solid-svg-icons'

export const ScheduleMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/schedule" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faCalendarDay} />
      <span>
        <Translate contentKey="global.menu.schedule">Schedule</Translate>
      </span>
    </NavLink>
  </NavItem>
);