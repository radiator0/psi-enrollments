import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileContract } from '@fortawesome/free-solid-svg-icons'

export const EnrollmentsMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/enrollments" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faFileContract} />
      <span>
        <Translate contentKey="global.menu.enrollments">Enrollments</Translate>
      </span>
    </NavLink>
  </NavItem>
);