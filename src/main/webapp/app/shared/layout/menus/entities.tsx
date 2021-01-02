import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/study-program">
      <Translate contentKey="global.menu.entities.studyProgram" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/field-of-study">
      <Translate contentKey="global.menu.entities.fieldOfStudy" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/semester">
      <Translate contentKey="global.menu.entities.semester" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/enrollment-unit">
      <Translate contentKey="global.menu.entities.enrollmentUnit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/enrollment-date">
      <Translate contentKey="global.menu.entities.enrollmentDate" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/enrollment-right">
      <Translate contentKey="global.menu.entities.enrollmentRight" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/student">
      <Translate contentKey="global.menu.entities.student" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/request">
      <Translate contentKey="global.menu.entities.request" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/specialty">
      <Translate contentKey="global.menu.entities.specialty" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/course">
      <Translate contentKey="global.menu.entities.course" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/class-group">
      <Translate contentKey="global.menu.entities.classGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/enrollment">
      <Translate contentKey="global.menu.entities.enrollment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/lecturer">
      <Translate contentKey="global.menu.entities.lecturer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/course-unit">
      <Translate contentKey="global.menu.entities.courseUnit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/selectable-module">
      <Translate contentKey="global.menu.entities.selectableModule" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/class-schedule">
      <Translate contentKey="global.menu.entities.classSchedule" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/building">
      <Translate contentKey="global.menu.entities.building" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/room">
      <Translate contentKey="global.menu.entities.room" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/class-unit">
      <Translate contentKey="global.menu.entities.classUnit" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
