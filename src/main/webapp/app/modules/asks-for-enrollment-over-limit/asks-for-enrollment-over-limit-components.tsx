import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons'

export const AsksForEnrollmentOverLimitMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/asks-for-enrollment-over-limit" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faQuestionCircle} />
      <span>
        <Translate contentKey="global.menu.asks-for-enrollment-over-limit">Asks for enrollment over limit</Translate>
      </span>
    </NavLink>
  </NavItem>
);
