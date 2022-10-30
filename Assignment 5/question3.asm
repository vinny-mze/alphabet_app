.data  
	prompt1: .asciiz "Enter n and formulae:\n"	
	prompt2: .asciiz "The values are:\n"
	nl: .asciiz     "\n"
	counter: .align 4
	
	
.text 
	.globl      main

		main:
		
			#print prompt1
			li	$v0, 4          
			la	$a0, prompt1
			
			syscall
			
			#get n
			li	$v0, 5				
			
			syscall
			
			la	$t0, counter
			move $t0, $v0
			
			li $t1, 0x0
			li $t2, 0x1
			li $t3, 0x0
			la $t5, counter

		printloop:
			beq $t1, $t0, exit

			
		
		
		printline:
			
			li	$v0, 12	
			syscall
			
			move $t4, $v0
			beq $t4, '=', startloop
			beq $t4, 0x0A,exitloop
			mul $t3,$t3, 10
			add $t3, $t3,$t4	
			sub $t3, $t3,48

			j printline
			
			add $t5,$t5, 10
			sw $t4, 0($t5)

		exitloop:

			la $t5, counter
			mul $t7, $t1, 0x20
			add $t5, $t5, $t7
			
			sw $t3, 0($t5)
			lw $t3, 0($t5)
			add $t1,$t1,1
			add $t3, $zero, $zero

			j printloop

		reset:
			li $t6, 0x0
			li $t7, 0x0

		clearReg:
			beq $t3,$t6,exitClear
			add $t7, $t7,0x20
			add $t6, $t6,1
			
			j clearReg
			
		exitClear:
			
			la $t5, counter
			add $t5, $t5, $t7
			
			lw $t3, 0($t5)
			mul $t5, $t1, 0x20
			
			la $t8, counter
			add $t5,$t5,$t8
			
			sw $t3, 0($t5)
			add $t1,$t1,1
			
			li $t3, 0
			j printloop
		
		startloop:
			
			li	$v0, 12				
			syscall
			
			move $t4, $v0
			beq $t4, 0x0A,reset
			
			mul $t3,$t3, 10
			add $t3, $t3,$t4
			sub $t3, $t3,48
			j startloop
			
		exit:
		
			li $t1, 0
			li	$v0, 4    
			la	$a0, prompt2
			
			syscall
			
			add $t4,$zero,$zero
			
		output:

			beq $t0,$t1,endloop
			la $t5, counter
			
			mul $t7, $t1,0x20
			add $t5, $t5, $t7
			lw $t3, 0($t5)
			move $a0, $t3
			

			li	$v0, 1          
			
			syscall
			
			#print newline
			add $t4,$t4,$t3
			add $t1, $t1, 1
			li	$v0, 4         
			la	$a0, nl
			
			syscall
		   
			j output
			
		endloop:
			move $a0, $t4
			
			#print integer
			li	$v0, 1          
			
			syscall
			
			li $v0, 10
			syscall