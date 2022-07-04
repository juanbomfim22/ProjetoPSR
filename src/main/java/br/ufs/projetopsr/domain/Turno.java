package br.ufs.projetopsr.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Turno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	 
	private String nome;

	//@JsonFormat(pattern="HH:mm")
	//private Date horaInicio;
	
	//@JsonFormat(pattern="HH:mm")
	//private Date horaTermino;
	
	private String horaInicio;
	
	private String horaTermino;
	
//	@JsonManagedReference
//	@ManyToOne
//	@JoinColumn(name="grade_id")
//	private Grade grade;
}
