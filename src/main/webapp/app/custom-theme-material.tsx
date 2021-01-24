import { createMuiTheme } from '@material-ui/core/styles';

const customTheme = createMuiTheme({
  palette: {
    primary: { main: '#b11902', contrastText: '#ffffff' },
    secondary: { main: '#fcd09f', contrastText: '#000000' },
  },
});
export default customTheme;
