import { IconButton, Button, MenuItem, createStyles, makeStyles, Theme, Menu, Tooltip } from '@material-ui/core';
import { AccountCircle } from '@material-ui/icons';
import React from 'react';
import { translate, Translate } from 'react-jhipster';
import TranslateIcon from '@material-ui/icons/Translate';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import { languages, locales } from 'app/config/translation';

export default function LanguageItem(props) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handleChange = langKey => {
    props.onClick(langKey);
    setAnchorEl(null);
  };
  return (
    <>
      <Tooltip title={translate('global.menu.language')}>
        <Button color="inherit" onClick={handleMenu}>
          <TranslateIcon />
          {props.currentLocale ? languages[props.currentLocale].name : undefined}
          <KeyboardArrowDownIcon />
        </Button>
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
        {locales
          ? locales.map(locale => (
              <MenuItem key={locale} onClick={() => handleChange(locale)}>
                {languages[locale].name}
              </MenuItem>
            ))
          : null}
      </Menu>
    </>
  );
}
