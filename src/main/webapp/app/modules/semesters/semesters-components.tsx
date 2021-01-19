import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons'

export const SemestersMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/semesters" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faCalendarAlt} />
      <span>
        <Translate contentKey="global.menu.semesters">Semesters</Translate>
      </span>
    </NavLink>
  </NavItem>
);