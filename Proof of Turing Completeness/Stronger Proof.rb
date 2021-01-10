=begin

The previous document "Simpler Proof" is easier to read and provides context for this program, so I recommend checking it out.

Lakshayati has features that make it somewhat easier to program in. However, if it did not have these features, would it still be
Turing-complete? To answer this question, I recreated the program found in the other proof in Ruby. For an additional challenge,
I decided all variable names can only contain the letter "o". The program works as intended, so, even with these contraints,
Lakshayati remains Turing-complete.

You can also find this program at https://repl.it/@MaxDobbs32/Proof-Lakshayati-is-Turing-Complete-in-Ruby#main.rb

=end

$oooooo = "$ooooooooooooooo"
$ooooooo = "oooooooooooooooo"
$ooooooooooooo = ""
$ooooooooooooooo = "$ooooooooooooo"
$ooooooooooooooooooo = "$ooooooooooooo"
$oooooooooooooooo = "$ooooo"
$oooooooooooooooooooo = "$ooooooooooooo"
$oooooooooooooooooooooooo = "$ooooooooooooo" 
$ooooooooooooooooo = "$ooooo"

$oooooooo = '''"""
  $oooooooooooo = \'$ooooo\'
  $ooooo = \'\'\'
    """+$oooooo+""" = \\"$ooooooooooooo\\"
    $oooooooooooo = \\"$ooooooooooooo\\"
  \'\'\'
  eval(eval("""+$oooooo+"""))
  $ooooo = \'"""+$oooooo+""" = \\"$ooooo\\"\'
  eval(eval($oooooooooooo))
"""'''

$o = eval($oooooooo)

$ooooooooooo = """
  $ooooo = '''
    print(\"Error: Data pointer commanded to move back relative to the starting position.\")
  '''
  eval(eval($oooooooooooooooo))
"""

$oooooooooo = '''"""
  $ooooo = \\"\\"\\"
    $o"""+$ooooooo+""" = \'$ooooooooooooo\'
    $ooooo"""+$ooooooo+""" = \'$ooooo\'
    """+$oooooo+"""oooo = \'$ooooooooooooo\'
    $ooooooooooo = $ooooooooooo + \'\'\'
      $ooooo = \\\\\\\\\'\\\\\\\\\'\\\\\\\\\'
        """+"$"+$ooooooo+"""oooooooo = \\\\\"$ooooooooooooo\\\\\"
        """+"$"+$ooooooo+"""oooo = \\\\\"$ooooooooooooo\\\\\"
        """+"$"+$ooooooo+""" = \\\\\"$ooooo\\\\\"
        $ooooooo = \\\\\""""+$ooooooo+"""\\\\\"
        $oooooo = \\\\\""""+$oooooo+"""\\\\\"
      \\\\\\\\\'\\\\\\\\\'\\\\\\\\\'
      eval(eval("""+"$"+$ooooooo+"""oooo))
    \'\'\'
  \\"\\"\\"
  eval(eval($o"""+$ooooooo+"""))
  """+"$"+$ooooooo+""" = \\"$ooooooooooooo\\"
  """+"$"+$ooooooo+"""oooo = \\"$ooooo\\"
  """+"$"+$ooooooo+"""oooooooo = \\"$ooooooooooooo\\"
  $oooooo = $oooooo + \\"oooo\\"
  $ooooooo = $ooooooo + \\"oooo\\"
"""'''

$oo = """
  $ooooooooo = eval($oooooooooo)
  eval($ooooooooo)
  $o = eval($oooooooo)
"""

$ooo = """
  eval($ooooooooooo)
  $o = eval($oooooooo)
"""

$oooo = """
  $oooooooooooo = \"$ooooo\"
  $ooooo = '''
    $oooooooooooo = \"$ooooooooooooo\"
    print(\"1\\n\")
  '''
  eval(eval(eval($oooooo)))
  $ooooo = 'print(\"0\\n\")'
  eval(eval($oooooooooooo))
"""


# The code below is just an example of SmallF code being emulated.

eval($oo)
eval($o)
eval($oo)
eval($o)
eval($oo)
eval($o)
eval($ooo)
eval($ooo)

$oooooooooooooooooo = '''
  $ooooo = """
    eval($ooo)
    eval($oooo)
    eval($oooooooooooooooooo)
  """
  eval(eval(eval($oooooo)))
'''

$oooooooooooooo = '''
  $ooooo = """
    eval($oo)
    eval($oooo)
    eval($oooooooooooooooooo)
    eval($oo)
    eval($o)
    eval($oo)
    eval($oooooooooooooo)
  """
  eval(eval(eval($oooooo)))
'''

eval($oooooooooooooo)
