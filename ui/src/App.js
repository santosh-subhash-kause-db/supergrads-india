import React from 'react';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import ProTip from './ProTip';
import VisitTracker from './VisitTracker';


export default function App() {
  return (
    <Container maxWidth="md">
      <Box my={4}>
        <Typography variant="h4" component="h1" gutterBottom>
          Supergrads - welcome to Deutsche Bank
        </Typography>
        <VisitTracker />
        <ProTip />
      </Box>
    </Container>
  );
}