Lakshayati is Turing-complete, meaning it can perform any computable function. To prove this, I have recreated another Turing-complete language called SmallF in Lakshayati. SmallFs < > and * operations can be typed at the bottom of this document, and SmallFs while-loops are emulated with recursion.

If you are curious why SmallF is Turing-complete, http://samuelhughes.com/boof/ shows an applicable proof relative to another Turing-complete language, BrainF. Then http://www.hevanet.com/cristofd/brainfuck/utm.b contains a proof of why BrainF is Turing-complete.


current_values_location "vv"
current_position "pp"
vv ""
vvv ""
pp "t"
ppp ""
pppp "" 
bool_pp "t"

change_string '''"""
  if_zero 't'
  t 「
    """ current_values_location """ ""
    if_zero ""
  」
  """ current_values_location """
  t '""" current_values_location """ "t"'
  if_zero
"""'''

"* " change_string

back_function """
  t '''
    / "Error: Data pointer commanded to move back relative to the starting position."
  '''
  pp
"""

forward_string '''"""
  t 「
    bool_""" current_position """ ''
    bool_p""" current_position """ 't'
    """ current_values_location """v ''
    back_function back_function 『
      t »
        """ current_position """pp ""
        """ current_position """p ""
        """ current_position """ "t"
        current_position '""" current_position """'
        current_values_location '""" current_values_location """'
      «
      """ current_position """p
    』
  」
  bool_""" current_position """
  """ current_position """ ""
  """ current_position """p "t"
  """ current_position """pp ""
  current_values_location current_values_location "v"
  current_position current_position "p"
"""'''

> '
  "forward_function " forward_string
  forward_function
  "* " change_string
'

< """
  back_function
  "* " change_string
"""

. """
  if_zero "t"
  t '
    if_zero ""
/ / "1
"
  '
  current_values_location
t '/ / "0
"'
  if_zero
"""


At_this_point_everthing_is_already_defined._The_code_below_is_just_an_example_of_SmallF_code_being_emulated.


>
*
>
*
>
*
<
<

fff '''
  t """
    <
    .
    fff
  """
  current_values_location
'''

ff '''
  t """
    >
    .
    fff
    >
    *
    >
    ff
  """
  current_values_location
'''

ff
