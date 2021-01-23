import { IconButton, Button, MenuItem, createStyles, makeStyles, Theme, Menu, Tooltip } from '@material-ui/core';
import { AccountCircle } from '@material-ui/icons';
import React from 'react';
import { translate, Translate } from 'react-jhipster';
import { Link, NavLink } from 'react-router-dom';

export default function AccountItem() {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <>
      <Tooltip title={translate('global.menu.account.main')}>
        <IconButton
          onClick={handleMenu}
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          color="inherit"
        >
          <AccountCircle />
        </IconButton>
      </Tooltip>
      <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={open}
        onClose={handleClose}
      >
        <Link to="/account/settings" style={{ textDecoration: 'none', color: 'inherit' }}>
          <MenuItem onClick={handleClose}>
            <Translate contentKey="global.menu.account.settings">Settings</Translate>
          </MenuItem>
        </Link>

        <Link to="/account/password" style={{ textDecoration: 'none', color: 'inherit' }}>
          <MenuItem onClick={handleClose}>
            <Translate contentKey="global.menu.account.password">Password</Translate>
          </MenuItem>
        </Link>

        <Link to="/logout" style={{ textDecoration: 'none', color: 'inherit' }}>
          <MenuItem onClick={handleClose}>
            <Translate contentKey="global.menu.account.logout">Sign out</Translate>
          </MenuItem>
        </Link>
      </Menu>
    </>
  );
}
