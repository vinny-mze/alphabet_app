.data
	myArray: .space 5000
	input: .space 5000
	getValues: .asciiz "Enter n, followed by n lines of text: \n"
	checker: .asciiz "This is the number entered: "
	printValues: .asciiz "The values are: \n"
	newline: .asciiz "\n"
			
.text
	main:
		la $s1,input #accept user input
	
		#print first sentence
		li $v0, 4
		la $a0, getValues
		
		syscall
		
		#get count of numbers entered by user
		li, $v0, 5
		
		syscall
		
		#storing the entered values into registers
		move $s0, $v0
		mul $s0, $s0, 4
		
		addi $t0, $zero, 0
		addi $t7, $zero, 0
		
		#asking user for more output
		while:  
			beq $s0, $t0, exit
			
			#receive more user input
			li, $v0, 8
			move $a0, $s1
			li $a1, 20
			syscall
			
			#storing input to data memory 
			move $t1, $a0
			sw $t1, myArray($t0)
		
			#overwriting PC address			
			addi $t0,$t0,4
			addi $s1,$s1,20
			
			#loop condition
			j while 
		exit:
			#end
			
		#output values
		li $v0, 4
		la $a0, printValues
		
		syscall
		
		addi $t0, $t0, -4

		loop_Through_Inputs:
			beq $t0, -4, end
			
			#print value
			li $v0, 4
			lw $a0, myArray($t0)
			syscall
			
			#updating PC adress
			addi $t0, $t0, -4
			 
			#loop condition
			j loop_Through_Inputs
		
		
		end:
			#exit main function
			li $v0, 10
			syscall
			
	li $v0,10
	syscall
