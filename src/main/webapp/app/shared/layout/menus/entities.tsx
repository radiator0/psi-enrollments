import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/study-program">
      <Translate contentKey="global.menu.entities.studyProgram" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/field-of-study">
      <Translate contentKey="global.menu.entities.fieldOfStudy" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/semester">
      <Translate contentKey="global.menu.entities.semester" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/enrollment-unit">
      <Translate contentKey="global.menu.entities.enrollmentUnit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/enrollment-date">
      <Translate contentKey="global.menu.entities.enrollmentDate" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/enrollment-right">
      <Translate contentKey="global.menu.entities.enrollmentRight" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/student">
      <Translate contentKey="global.menu.entities.student" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/request">
      <Translate contentKey="global.menu.entities.request" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/specialty">
      <Translate contentKey="global.menu.entities.specialty" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course">
      <Translate contentKey="global.menu.entities.course" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-group">
      <Translate contentKey="global.menu.entities.classGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/enrollment">
      <Translate contentKey="global.menu.entities.enrollment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lecturer">
      <Translate contentKey="global.menu.entities.lecturer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-unit">
      <Translate contentKey="global.menu.entities.courseUnit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/selectable-module">
      <Translate contentKey="global.menu.entities.selectableModule" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-schedule">
      <Translate contentKey="global.menu.entities.classSchedule" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/building">
      <Translate contentKey="global.menu.entities.building" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/room">
      <Translate contentKey="global.menu.entities.room" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-unit">
      <Translate contentKey="global.menu.entities.classUnit" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
